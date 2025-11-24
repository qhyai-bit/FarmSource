package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerAnimal;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ManagerAnimalMapper {

    //查询指定动物
    ManagerAnimal selectByPrimaryKey(String drAnimalId);
    //更新动物健康状态
    int updateHealthyByAnimalId(String healthy, String animalId);

    //更新动物信息
    int updateByPrimaryKey(ManagerAnimal animal);
    //新增动物
    int insert(ManagerAnimal animal);

    //统计各区间动物数量
    Map<String, Integer> countWeight();
}