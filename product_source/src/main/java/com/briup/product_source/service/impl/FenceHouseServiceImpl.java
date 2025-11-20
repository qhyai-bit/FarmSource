package com.briup.product_source.service.impl;

import com.briup.product_source.dao.ManagerFenceHouseMapper;
import com.briup.product_source.dao.ManagerHurdlesMapper;
import com.briup.product_source.dao.ext.ManagerFenceHouseExtMapper;
import com.briup.product_source.exception.ServiceException;
import com.briup.product_source.pojo.ManagerFenceHouse;
import com.briup.product_source.pojo.ext.ManagerFenceHouseExt;
import com.briup.product_source.result.ResultCode;
import com.briup.product_source.service.FenceHouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FenceHouseServiceImpl implements FenceHouseService {
    @Autowired
    private ManagerFenceHouseMapper houseMapper;
    @Autowired
    private ManagerFenceHouseExtMapper houseExtMapper;
    //用来操作栏圈
    @Autowired
    private ManagerHurdlesMapper hurdlesMapper;

    @Override
    public PageInfo<ManagerFenceHouse> findByPage(Integer pageNum, Integer pageSize, String fhName) {
        //1.PageHelper开启分页
        PageHelper.startPage(pageNum, pageSize);
        //2.核心查询语句
        List<ManagerFenceHouse> list = houseMapper.queryAllHouses(fhName);
        //3.将查询的信息封装在PageInfo对象中，返回
        PageInfo<ManagerFenceHouse> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ManagerFenceHouseExt findById(String id) {
        ManagerFenceHouseExt houseExt = houseExtMapper.selectHouseAndHurdlesById(id);
        if (houseExt == null) {
            throw new ServiceException(ResultCode.FAIL);
        }
        return houseExt;
    }

    @Override
    public void saveOrUpdate(ManagerFenceHouse house) {
        //1.参数校验：栏舍名称必须存在 且 不能为空白字符串
        if (house == null || !StringUtils.hasText(house.getFhName())){
            throw new ServiceException(ResultCode.PARAM_IS_EMPTY);
        }
        int updateCount = 0;
        //2.判断是修改还是新增
        String fhId = house.getFhId();
        if(StringUtils.hasText(fhId)) {
            //2.1 修改
            //a.根据fhId查询 栏舍是否存在
            ManagerFenceHouse houseDB = houseMapper.selectByPrimaryKey(fhId);
            if (houseDB == null)//栏舍不存在
                throw new ServiceException(ResultCode.FENCE_HOUSE_NOT_EXIST);

            //b.判断fhName是否有效[唯一 或 原值]
            String fhName = house.getFhName();
            if(isExist(fhName) && !fhName.equals(houseDB.getFhName()))//栏舍名称已经存在
                throw new ServiceException(ResultCode.FENCE_HOUSENAME_IS_EXIST);

            //c.更新操作
            updateCount = houseMapper.updateByPrimaryKey(house);//底层调用updateByPrimaryKey方法
        } else {
            //2.2 新增
            //a.栏舍名称 唯一校验
            if (isExist(house.getFhName()))//栏舍名称已经存在
                throw new ServiceException(ResultCode.FENCE_HOUSENAME_IS_EXIST);
            //b.自动生成id值
            String id = UUID.randomUUID().toString().replace("-", "");
            house.setFhId(id);
            //c.新增栏舍
            updateCount = houseMapper.insert(house);
        }
        if(updateCount == 0)
            throw new ServiceException(ResultCode.FAIL);
    }

    @Override
    public void removeById(String fhId) {
        //1.参数校验
        if (!StringUtils.hasText(fhId))//fhId为空
            throw new ServiceException(ResultCode.PARAM_IS_EMPTY);
        //2.fhId有效判断(存在)
        ManagerFenceHouse houseDB = houseMapper.selectByPrimaryKey(fhId);
        if (houseDB == null)//栏舍不存在
            throw new ServiceException(ResultCode.FENCE_HOUSE_NOT_EXIST);
        //3.判断栏舍下是否包含栏圈
        int count = hurdlesMapper.selectCountByFhId(fhId);
        if (count > 0) {//栏舍下有栏圈
            throw new ServiceException(ResultCode.DATA_CAN_NOT_DELETE);
        }
        //4.删除指定栏舍
        houseMapper.deleteByPrimaryKey(fhId);
    }

    //提示：请先理解逻辑外键 物理外键
    @Override
    public void removeBatch(List<String> ids) {
        //1.参数校验
        if (ids == null || ids.isEmpty())
            throw new ServiceException(ResultCode.PARAM_IS_EMPTY);
        //2.无效id剔除【id对应的栏舍不存在、栏舍下存在栏圈】
        List<String> list = new ArrayList<>();
        for (String id : ids) {
            //2.1 判断id是否存在
            ManagerFenceHouse houseDB = houseMapper.selectByPrimaryKey(id);
            if (houseDB == null)//栏舍不存在
                continue;//跳过当前id
            //2.2 判断栏舍下是否存在栏圈
            int count = hurdlesMapper.selectCountByFhId(id);
            if (count > 0)//栏舍下有栏圈
                continue;//跳过当前id
            //2.3 将有效id添加到list中
            list.add(id);
        }
        //如果没有有效的id，则抛出异常
        if (list.isEmpty())
            throw new ServiceException(ResultCode.FENCE_HOUSE_ID_INVALID);
        //3.批量删除list
        int deleteCount = houseMapper.deleteBatchByIds(list);
        if (deleteCount == 0)//删除失败
            throw new ServiceException(ResultCode.FAIL);
    }

    @Override
    public List<ManagerFenceHouse> findAll() {
        return houseMapper.findAll();
    }

    //工具方法：判断fh_name是否已经存在
    private boolean isExist(String fhName) {
        ManagerFenceHouse house = houseMapper.selectByFhName(fhName);
        return house != null;//house不为null表示已经存在
    }
}
