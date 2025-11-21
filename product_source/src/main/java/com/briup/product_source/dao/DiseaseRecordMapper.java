package com.briup.product_source.dao;

import com.briup.product_source.pojo.DiseaseRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRecordMapper {
    // 根据主键查询治疗记录
    DiseaseRecord selectByPrimaryKey(Integer drId);
    //更新指定治疗记录
    int updateByPrimaryKey(DiseaseRecord record);
    //新增治疗记录
    int insert(DiseaseRecord record);
}