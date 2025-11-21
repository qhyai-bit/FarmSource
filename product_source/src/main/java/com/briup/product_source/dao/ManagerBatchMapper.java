package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerBatch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerBatchMapper {
    //查询所有未检疫的批次信息
    List<ManagerBatch> selectAllUnquarantined();

    //获取批次信息
    ManagerBatch selectByPrimaryKey(String batchId);
    //更新批次表中检疫状态
    int updateQualifiedById(String bQualified, String batchId);
}