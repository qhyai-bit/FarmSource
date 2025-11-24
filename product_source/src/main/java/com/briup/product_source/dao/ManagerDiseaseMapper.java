package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerDisease;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ManagerDiseaseMapper {
    //查询所有病症信息
    List<ManagerDisease> selectAll();

    //根据各类疾病 统计 患病动物数量
    List<Map<String, Object>> countDisease();
}