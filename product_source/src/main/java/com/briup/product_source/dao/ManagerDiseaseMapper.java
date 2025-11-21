package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerDisease;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerDiseaseMapper {
    //查询所有病症信息
    List<ManagerDisease> selectAll();
}