package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerAnimal;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerAnimalMapper {

    //查询指定动物
    ManagerAnimal selectByPrimaryKey(String drAnimalId);
    //更新动物健康状态
    int updateHealthyByAnimalId(String healthy, String animalId);
}