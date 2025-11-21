package com.briup.product_source.service;

import com.briup.product_source.pojo.QuarantineRegistration;
import com.github.pagehelper.PageInfo;

public interface QuarantineRegistrationService {
    //条件+分页查询 检疫记录
    PageInfo<QuarantineRegistration> findByPage(Integer pageNum,
                                                Integer pageSize,
                                                String mechanism,
                                                String bQualified);

    //新增或更新检疫记录
    void saveOrUpdate(QuarantineRegistration recode);
}
