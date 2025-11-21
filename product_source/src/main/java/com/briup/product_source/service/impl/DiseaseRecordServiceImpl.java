package com.briup.product_source.service.impl;

import com.briup.product_source.dao.DiseaseRecordMapper;
import com.briup.product_source.dao.ManagerAnimalMapper;
import com.briup.product_source.dao.ManagerDiseaseMapper;
import com.briup.product_source.dao.ext.DiseaseRecordExtMapper;
import com.briup.product_source.exception.ServiceException;
import com.briup.product_source.pojo.DiseaseRecord;
import com.briup.product_source.pojo.ManagerAnimal;
import com.briup.product_source.pojo.ManagerDisease;
import com.briup.product_source.pojo.ext.DiseaseRecordExt;
import com.briup.product_source.result.ResultCode;
import com.briup.product_source.service.DiseaseRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DiseaseRecordServiceImpl implements DiseaseRecordService {
    @Autowired
    private ManagerDiseaseMapper diseaseMapper;
    //额外注入扩展mapper接口对象
    @Autowired
    private DiseaseRecordExtMapper dRecordExtMapper;
    //注意：额外新增 动物Mapper接口
    @Autowired
    private ManagerAnimalMapper animalMapper;
    //注意：额外新增 治疗记录Mapper接口
    @Autowired
    private DiseaseRecordMapper dRecordMapper;

    @Override
    public List<ManagerDisease> findAllDiseases() {
        return diseaseMapper.selectAll();
    }

    @Override
    public PageInfo<DiseaseRecordExt> findByPage(Integer pageNum,
                                                 Integer pageSize,
                                                 String drStatus,
                                                 Integer drDId) {
        //1.开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //2.条件查询
        List<DiseaseRecordExt> list = dRecordExtMapper.selectDiseasedAnimal(drStatus, drDId);
        //3.封装分页对象并返回
        PageInfo<DiseaseRecordExt> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveOrUpdate(DiseaseRecord record) {
        String animalId = record.getDrAnimalId();//获取动物编号
        //1.参数校验[动物编号、病症描述不能为空]
        if (!StringUtils.hasText(animalId) ||
                !StringUtils.hasText(record.getDrDesc())){
            throw new ServiceException(ResultCode.PARAM_IS_EMPTY);
        }
        //2.动物校验
        ManagerAnimal animalFromDB = animalMapper.selectByPrimaryKey(animalId);//根据动物编号查询动物
        //2.1 动物不存在抛出异常
        if (animalFromDB == null)
            throw new ServiceException(ResultCode.FAIL);
        //2.2 动物状态不是"养殖中"，抛出异常
        if (!animalFromDB.getAStatus().equals("养殖中"))
            throw new ServiceException(ResultCode.ANIMAL_IS_NOT_IN_BREEDING);
        //3.添加或者修改诊疗记录
        // sql语句执行后，返回受影响的行数result
        int result;
        Integer drId = record.getDrId();//获取id
        String drStatus = record.getDrStatus();//获取状态
        if (drId != null) {
            //3.1 有id->更新操作
            if (dRecordMapper.selectByPrimaryKey(drId) == null)
                throw new ServiceException(ResultCode.PARAM_IS_EMPTY);

            result = dRecordMapper.updateByPrimaryKey(record);//执行更新
        } else {
            //3.2 无id->新增操作
            // 未传诊疗状态，默认为未治疗
            if (!StringUtils.hasText(drStatus)) {
                record.setDrStatus("未治疗");
            }
            //新增记录
            result = dRecordMapper.insert(record);
        }
        // 添加病症记录失败
        if (result == 0) {
            throw new ServiceException(ResultCode.FAIL);
        }
        //4.添加病症记录成功，根据诊疗状态修改动物健康状态
        String healthy = "健康";
        if (!"已治疗".equals(drStatus)) {
            healthy = "生病";
        }
        if (animalMapper.updateHealthyByAnimalId(healthy, animalId) == 0) {
            throw new ServiceException(ResultCode.FAIL);
        }
    }
}
