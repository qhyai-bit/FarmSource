package com.briup.product_source.service.impl;

import com.briup.product_source.dao.ManagerHurdlesMapper;
import com.briup.product_source.exception.ServiceException;
import com.briup.product_source.pojo.ManagerHurdles;
import com.briup.product_source.pojo.ext.ManagerHurdlesExt;
import com.briup.product_source.result.ResultCode;
import com.briup.product_source.service.HurdlesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HurdlesServiceImpl implements HurdlesService {
    @Autowired
    private ManagerHurdlesMapper hurdlesMapper;

    @Override
    public List<Integer> findAllMax() {
        return hurdlesMapper.selectAllMax();
    }

    @Override
    public PageInfo<ManagerHurdlesExt> findByPage(
            Integer pageNum, Integer pageSize,
            String hName, Integer hMax,
            String fhName, String hEnable) {
        //1.开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //2.执行条件查询
        List<ManagerHurdlesExt> list = hurdlesMapper.findHurdlesWithHouseByConditions(hName, hMax, fhName, hEnable);
        //3.封装结果并返回
        PageInfo<ManagerHurdlesExt> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void modifyStatus(String hId, String hEnable) {
        //1.判断栏圈是否存在
        ManagerHurdles hurdlesDB = hurdlesMapper.selectByPrimaryKey(hId);
        if (hurdlesDB == null)//栏圈不存在
            throw new ServiceException(ResultCode.DATA_IS_EMPTY);
        //2.调整状态值【解决传参bug】
        //System.out.println("in service, hEnable: " + hEnable);
        if ("可用".equals(hEnable)) {
            hEnable = "禁用";
        }else {
            hEnable = "可用";
        }
        //3.更新栏圈状态
        if(hurdlesMapper.updateEnableById(hId, hEnable) == 0){
            throw new ServiceException(ResultCode.FAIL);
        }
    }

    @Override
    public void modifyStatusBatch(List<Map<String, String>> list) {
        int sum = 0;
        //1.遍历list集合，解析出每项 hId - hEnable
        for (Map<String, String> map : list) {
            //1.1 提取hId 并判断是否存在
            String hId = map.get("hId");
            if (hurdlesMapper.selectByPrimaryKey(hId) == null) {
                continue;
            }
            //1.2 提取hEnable 并解决参数bug问题
            String hEnable = map.get("hEnable");
            String status = "可用";
            if (status.equals(hEnable)) {
                status = "禁用";
            }
            //1.3 更新栏圈状态，并记录成功条数
            sum  += hurdlesMapper.updateEnableById(hId, status);
        }
        //2.如果全部更新失败，抛出异常
        if (sum == 0)
            throw new ServiceException(ResultCode.FAIL);
    }
}
