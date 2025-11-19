package com.briup.product_source.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ManagerHurdlesMapper {
    // 根据栏舍编号查询该栏舍有多少个栏圈
    int selectCountByFhId(String fhId);
}
