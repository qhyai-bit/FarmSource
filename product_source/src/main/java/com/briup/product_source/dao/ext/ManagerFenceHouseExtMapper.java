package com.briup.product_source.dao.ext;

import com.briup.product_source.pojo.ext.ManagerFenceHouseExt;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerFenceHouseExtMapper {
    //查询指定栏舍(含其下所有栏圈)
    ManagerFenceHouseExt selectHouseAndHurdlesById(String fhId);
}