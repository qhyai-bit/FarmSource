package com.briup.product_source.service;

import com.briup.product_source.pojo.ManagerHurdles;
import com.briup.product_source.pojo.ext.ManagerHurdlesExt;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface HurdlesService {
    //查询所有栏圈最大容量
    List<Integer> findAllMax();

    //分页+条件查询栏圈(含栏舍)
    PageInfo<ManagerHurdlesExt> findByPage(
            Integer pageNum, Integer pageSize,
            String hName, Integer hMax,
            String fhName, String hEnable);

    //修改hId栏圈的状态为hEnable
    void modifyStatus(String hId, String hEnable);

    //批量修改栏圈的可用状态
    void modifyStatusBatch(List<Map<String, String>> list);

    //查询所有栏圈信息(可用、未满)
    List<ManagerHurdles> findAllEnable();
}
