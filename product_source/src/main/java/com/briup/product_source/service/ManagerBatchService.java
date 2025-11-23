package com.briup.product_source.service;

import com.briup.product_source.pojo.ManagerBatch;

import java.util.List;

public interface ManagerBatchService {

    //查询所有未检疫的批次信息
    List<ManagerBatch> findAllUnquarantined();

    //查询所有批次信息
    List<ManagerBatch> findAll();
}
