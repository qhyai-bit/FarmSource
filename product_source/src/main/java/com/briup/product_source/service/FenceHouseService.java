package com.briup.product_source.service;

import com.briup.product_source.pojo.ManagerFenceHouse;
import com.briup.product_source.pojo.ext.ManagerFenceHouseExt;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface FenceHouseService {
    /**
     * 多条件分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param fhName 栏舍名称
     * @return 分页对象
     */
    PageInfo<ManagerFenceHouse> findByPage(Integer pageNum, Integer pageSize, String fhName);

    /**
     * 根据id查询栏舍信息及其对应的栏圈信息
     * @param id 栏舍id
     * @return ManagerFenceHouseExtend类型是一个自定义类型，用来实现1对
    多的映射
     */
    ManagerFenceHouseExt findById(String id);

    /**
     * 保存或者更新
     * @param house 栏舍信息对象
     * 注意：如果id存在则为修改，不存在则为新增
     */
    void saveOrUpdate(ManagerFenceHouse house);

    /**
     * 根据id删除栏舍信息
     * @param fhId 栏舍id
     */
    void removeById(String fhId);

    /**
     * 批量删除
     * @param ids 栏舍id
     */
    void removeBatch(List<String> ids);
}

