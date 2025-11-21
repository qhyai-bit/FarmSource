package com.briup.product_source.controller;

import com.briup.product_source.pojo.QuarantineRegistration;
import com.briup.product_source.result.Result;
import com.briup.product_source.service.QuarantineRegistrationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "检疫登记模块")
@RestController
@RequestMapping("/quarantineRegistration")
public class QuarantineController {
    @Autowired
    private QuarantineRegistrationService qrService;

    @ApiOperation("分页多条件查询检疫记录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",defaultValue = "5", required = true, paramType = "query"),
            @ApiImplicitParam(name = "grMechanism", value = "检疫机构", paramType = "query"),
            @ApiImplicitParam(name = "bQualified", value = "检疫结果", paramType = "query")
    })
    @GetMapping
    public Result findByPage(Integer pageNum, Integer pageSize, String grMechanism, String bQualified) {
        PageInfo<QuarantineRegistration> pageInfo = qrService.findByPage(pageNum, pageSize, grMechanism, bQualified);
        return Result.success(pageInfo);
    }

    @ApiOperation("新增或更新检疫记录接口")
    @PostMapping("/saveOrUpdate")
    public Result reviseRegistration(@RequestBody QuarantineRegistration qr) {
        qrService.saveOrUpdate(qr);
        return Result.success();
    }
}
