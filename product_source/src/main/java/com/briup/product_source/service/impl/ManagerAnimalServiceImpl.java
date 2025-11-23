package com.briup.product_source.service.impl;

import com.briup.product_source.dao.ManagerAnimalMapper;
import com.briup.product_source.dao.ManagerBatchMapper;
import com.briup.product_source.dao.ManagerHurdlesMapper;
import com.briup.product_source.dao.ext.ManagerAnimalExtMapper;
import com.briup.product_source.exception.ServiceException;
import com.briup.product_source.pojo.ManagerAnimal;
import com.briup.product_source.pojo.ManagerBatch;
import com.briup.product_source.pojo.ManagerHurdles;
import com.briup.product_source.pojo.ext.ManagerAnimalExt;
import com.briup.product_source.result.ResultCode;
import com.briup.product_source.service.ManagerAnimalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerAnimalServiceImpl implements ManagerAnimalService {
    @Autowired
    private ManagerAnimalExtMapper animalExtMapper;
    //注意：该业务功能除了要操作动物，还需要操作批次和栏圈
    @Autowired
    private ManagerAnimalMapper animalMapper;
    @Autowired
    private ManagerBatchMapper batchMapper;
    @Autowired
    private ManagerHurdlesMapper hurdlesMapper;
    @Override
    public PageInfo<ManagerAnimalExt> findByPage(
            Integer pageNum,
            Integer pageSize,
            String aHealthy,
            String aGender) {
        //1.开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //2.条件查询
        List<ManagerAnimalExt> list = animalExtMapper.selectAnimalRelated(aHealthy, aGender);
        //3.封装分页对象并返回
        PageInfo<ManagerAnimalExt> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //新增或修改动物，该功能业务较为复杂，涉及批次、栏圈模块，请静心认真分析
    @Override
    public void saveOrUpdate(ManagerAnimal animal) {
        //1.参数校验,体重不为空(其他字段也非空，前端已处理，此处不需要处理)
        if (animal == null || !StringUtils.hasText(animal.getAWeight())) {
            throw new ServiceException(ResultCode.PARAM_IS_EMPTY);
        }
        //2.判断栏圈是否有效存在
        // 新栏圈编号
        String aHurdlesIdNew = animal.getAHurdlesId();
        ManagerHurdles hurdlesNew = hurdlesMapper.selectByPrimaryKey(aHurdlesIdNew);//获取新栏圈信息
        if (hurdlesNew == null) {
            throw new ServiceException(ResultCode.HURDLES_NOT_EXIST);
        }
        //3.添加或者修改动物信息
        String aAnimalId = animal.getAAnimalId();//新增或修改的动物id
        if (StringUtils.hasText(aAnimalId)) {
            //3.1 有id->更新操作
            // a.根据id查询动物信息
            ManagerAnimal animalFromDB = animalMapper.selectByPrimaryKey(aAnimalId);
            if (animalFromDB == null) {
                throw new ServiceException(ResultCode.ANIMAL_NOT_EXIST);
            }
            // b.修改动物信息
            int result = animalMapper.updateByPrimaryKey(animal);
            if (result == 0)
                throw new ServiceException(ResultCode.FAIL);
            // c.判断是否修改了栏圈信息
            String aHurdlesIdOld = animalFromDB.getAHurdlesId();
            if (!aHurdlesIdOld.equals(aHurdlesIdNew)) {
                //动物栏圈发生改变
                // 1.新栏圈动物数量 + 1，并重置空满状态
                // 动物数量 + 1
                Integer hSaved = hurdlesNew.getHSaved();
                hurdlesNew.setHSaved(hSaved + 1);
                // 重置新栏圈空满状态
                if(hSaved + 1 == hurdlesNew.getHMax()) {
                    hurdlesNew.setHFull("已满");
                }
                // 执行更新操作
                hurdlesMapper.updateByPrimaryKey(hurdlesNew);
                // 2.老栏圈动物数量 - 1，并重置空满状态
                ManagerHurdles hurdlesOld = hurdlesMapper.selectByPrimaryKey(aHurdlesIdOld);
                // 动物数量 - 1
                hSaved = hurdlesOld.getHSaved();
                hurdlesOld.setHSaved(hSaved - 1);
                // 重置老栏圈空满状态
                if ("已满".equals(hurdlesOld.getHFull())) {
                    hurdlesOld.setHFull("未满");
                }
                // 执行更新
                hurdlesMapper.updateByPrimaryKey(hurdlesOld);
            }
        } else {
            // 3.2 无id->新增操作
            // a.批次有效判断
            ManagerBatch batchFromDB = batchMapper.selectByPrimaryKey(animal.getABatchId());//获取批次信息
            if (batchFromDB == null) {
                throw new ServiceException(ResultCode.BATCH_NOT_EXIST);
            }
            // b.根据批次检疫状态，设置动物的养殖状态
            String bQuarantine = batchFromDB.getBQuarantine();//批次检疫状态
            if ("已检疫".equals(bQuarantine)) {
                animal.setAStatus("已检疫");
            } else {
                animal.setAStatus("养殖中");
            }
            // c.添加动物信息
            animal.setAAnimalId(UUID.randomUUID().toString().replace("-", ""));
            int result = animalMapper.insert(animal);
            if (result == 0)
                throw new ServiceException(ResultCode.FAIL);
            // d.对应栏圈 动物数量 + 1，存储状态重置
            Integer hSaved = hurdlesNew.getHSaved();
            hurdlesNew.setHSaved(hSaved + 1);
            if(hSaved+1 == hurdlesNew.getHMax()) {
                hurdlesNew.setHFull("已满");
            }
            hurdlesMapper.updateByPrimaryKey(hurdlesNew);
        }
    }
}
