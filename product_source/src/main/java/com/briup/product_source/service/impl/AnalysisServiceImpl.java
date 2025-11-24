package com.briup.product_source.service.impl;

import com.briup.product_source.dao.IssueRecordMapper;
import com.briup.product_source.dao.ManagerAnimalMapper;
import com.briup.product_source.dao.ManagerDiseaseMapper;
import com.briup.product_source.dao.ManagerFenceHouseMapper;
import com.briup.product_source.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private ManagerFenceHouseMapper houseMapper;
    @Autowired
    private IssueRecordMapper issueRecordMapper;
    @Autowired
    private ManagerDiseaseMapper diseaseMapper;
    @Autowired
    private ManagerAnimalMapper animalMapper;

    @Override
    public Map<String, List<Object>> countNum() {
        //1.准备keys
        List<Object> names = Arrays.asList("栏舍", "栏圈", "动物", "冷库", "员工");
        //2.根据keys查询对应的数量值
        List<Object> values = houseMapper.countAllResources();
        //3.组合map集合对象并返回
        HashMap<String, List<Object>> map = new HashMap<>();
        map.put("name", names);
        map.put("value", values);
        return map;
    }

    @Override
    public Map<String, List<Object>> countSales() {
        //1.准备月份列表
        String[] month = {"1月", "2月", "3月", "4月", "5月", "6月",
                "7月", "8月", "9月", "10月", "11月", "12月"};
        List<Object> months = Arrays.asList(month);//创建月份列表
        //2.准备对应销售总数列表，默认销量为0
        Integer[] sales = new Integer[12];
        Arrays.fill(sales, 0);//填充数组元素为0
        List<Object> salesList = Arrays.asList(sales);//创建销量列表
        //3.查询本年每月销售数量，结果为 List<Map<String, Integer>>list;
        /* list内容如下：
        [
            {销售总数=3, 月份=6},
            {销售总数=1, 月份=5},
            {销售总数=2, 月份=4},
            ...
        ]
        */
        List<Map<String, Integer>> baseData = issueRecordMapper.countSales();//查询数据
        //System.out.println("baseData: " + baseData);
        //4.解析数据库查询的月销量数据，并更新到销量列表中
        for (Map<String, Integer> map : baseData) {
            //获取指定月份索引
            int index = map.get("月份") - 1;
            //获取对应月份销量值
            //【bug解决】：默认情况下 销售总数类型为 BigDecimal，其无法直接转换成 Integer类型
            //直接转换服务器会报错：java.math.BigDecimal cannot be cast to java.lang.Integer
            int count = new BigDecimal(map.get("销售总数")+"").intValue();//转换为int类型
            //System.out.println("index: " + index + " count: " + count);
            //更新销售列表中 指定月份 销售数量值
            salesList.set(index, count);
        }
        //5.封装响应结果对象map 并返回
        HashMap<String, List<Object>> map = new HashMap<>();
        map.put("name", months);
        map.put("value", salesList);
        //System.out.println("封装好的map: " + map);
        return map;
    }

    @Override
    public Map<String, Long> countDisease() {
        //1.查询 病症 及 患病动物数量
        /* 结果参考：
            [
                {d_name=流行性感冒, sum=10},
                {d_name=传染性胃肠炎, sum=3},
                {d_name=维生素A缺乏症, sum=2}
            ]
        */
        List<Map<String, Object>> mapList = diseaseMapper.countDisease();//查询数据
        //System.out.println("mapList: " + mapList);
        //2.根据查询结果 封装map对象 并返回
        HashMap<String, Long> map = new HashMap<>();
        for (Map<String, Object> e : mapList) {
            //获取键
            String key = String.valueOf(e.get("d_name"));
            //获取值
            Long value = (Long) e.get("sum");
            map.put(key, value);
        }
        //简化成lambda表达式
        //mapList.forEach(e -> { map.put(String.valueOf(e.get("d_name")), (Long)e.get("sum"));});
        return map;
    }

    @Override
    public Map<String, Integer> countWeight() {
        return animalMapper.countWeight();
    }
}
