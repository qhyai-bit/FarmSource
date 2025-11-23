package com.briup.product_source.controller;

import com.briup.product_source.pojo.ManagerBatch;
import com.briup.product_source.result.Result;
import com.briup.product_source.service.ManagerBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "批次记录模块")
@RestController
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    private ManagerBatchService batchService;

    @ApiOperation("查询所有未检疫的批次信息接口")
    @GetMapping("/queryAllUnquarantined")
    public Result queryAllUnquantified() {
        List<ManagerBatch> batches = batchService.findAllUnquarantined();
        return Result.success(batches);
    }

    @ApiOperation("查询所有批次信息接口")
    @GetMapping("/queryAll")
    public Result queryAllBatches() {
        List<ManagerBatch> batches = batchService.findAll();
        return Result.success(batches);
    }
}