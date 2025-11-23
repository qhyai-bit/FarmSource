package com.briup.product_source.service;

import com.briup.product_source.pojo.ManagerAnimal;
import com.briup.product_source.pojo.ext.ManagerAnimalExt;
import com.github.pagehelper.PageInfo;

public interface ManagerAnimalService {
    //分页多条件查询动物信息(含栏圈名称、栏舍名称及批次信息)
    PageInfo<ManagerAnimalExt> findByPage(Integer pageNum,
                                          Integer pageSize,
                                          String aHealthy,
                                          String aGender);

    //新增或更新动物信息
    void saveOrUpdate(ManagerAnimal animal);
}
