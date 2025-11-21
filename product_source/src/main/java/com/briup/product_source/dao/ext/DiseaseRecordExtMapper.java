package com.briup.product_source.dao.ext;

import com.briup.product_source.pojo.ext.DiseaseRecordExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRecordExtMapper {
    //条件查询 治疗记录信息(含病症信息)
    List<DiseaseRecordExt> selectDiseasedAnimal(String drStatus, Integer drDId);
}