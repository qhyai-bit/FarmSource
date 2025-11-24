package com.briup.product_source.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IssueRecordMapper {
    // 统计本年度每个月动物销量
    List<Map<String, Integer>> countSales();
}