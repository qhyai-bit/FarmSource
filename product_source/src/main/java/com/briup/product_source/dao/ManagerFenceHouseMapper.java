package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerFenceHouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerFenceHouseMapper {
    //查询所有栏舍
    List<ManagerFenceHouse> queryAllHouses(String fhName);

    //根据栏舍名称查询指定栏舍对象
    ManagerFenceHouse selectByFhName(String fhName);
    //新增栏舍对象
    int insert(ManagerFenceHouse house);
    //根据栏舍编号查询指定栏舍对象
    ManagerFenceHouse selectByPrimaryKey(String fhId);
    //更新栏舍对象
    int updateByPrimaryKey(ManagerFenceHouse house);

    //根据栏舍编号删除指定栏舍
    int deleteByPrimaryKey(String fhId);

    //批量删除栏舍
    int deleteBatchByIds(List<String> ids);

    //查询所有栏舍
    List<ManagerFenceHouse> findAll();
}