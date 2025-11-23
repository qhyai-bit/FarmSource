package com.briup.product_source.dao;

import com.briup.product_source.pojo.ManagerHurdles;
import com.briup.product_source.pojo.ext.ManagerHurdlesExt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerHurdlesMapper {
    // 根据栏舍编号查询该栏舍有多少个栏圈
    int selectCountByFhId(String fhId);

    // 查询所有栏圈的最大容量【注意去重复值】
    List<Integer> selectAllMax();

    //多条件查询栏圈信息
    List<ManagerHurdlesExt> findHurdlesWithHouseByConditions(
                                                String hName,
                                                Integer hMax,
                                                String fhName,
                                                String hEnable);

    //查询指定栏圈
    ManagerHurdles selectByPrimaryKey(String hId);
    //更新指定栏圈启用状态
    int updateEnableById(String hId, String hEnable);

    //查询所有可用、未满栏圈
    List<ManagerHurdles> selectAllEnable();

    //更新栏圈信息
    int updateByPrimaryKey(ManagerHurdles hurdles);
}
