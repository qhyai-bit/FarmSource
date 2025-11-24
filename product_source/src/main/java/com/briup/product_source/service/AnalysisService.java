package com.briup.product_source.service;

import java.util.List;
import java.util.Map;

public interface AnalysisService {
    //统计栏舍、栏圈、动物、冷库、员工数量
    Map<String, List<Object>> countNum();

    //统计本年度每月动物销量
    Map<String, List<Object>> countSales();

    //统计患各类疾病的动物数量
    Map<String, Long> countDisease();

    //按体重(30以下 30-50 50以上)统计动物数量
    Map<String, Integer> countWeight();

}
