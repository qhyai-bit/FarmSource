package com.briup.product_source.controller;

import com.briup.product_source.result.Result;
import com.briup.product_source.service.AnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "首页大屏模块")
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;

    @ApiOperation("统计栏舍、栏圈、动物、冷库、员工数量接口")
    @GetMapping("/count")
    public Result countAll() {
        Map<String, List<Object>> count = analysisService.countNum();
        return Result.success(count);
    }

    @ApiOperation("统计本年度12个月动物销量")
    @GetMapping("/countSales")
    public Result countAnnualSales(){
        Map<String, List<Object>> map = analysisService.countSales();
        return Result.success(map);
    }

    @ApiOperation("统计动物病症数量接口")
    @GetMapping("/countDisease")
    public Result getAnimalDiseased() {
        Map<String, Long> map = analysisService.countDisease();
        return Result.success(map);
    }

    @ApiOperation(value = "按体重统计动物数量", notes = "30以下|30-50|50以上")
    @GetMapping("/indexCount")
    public Result getAnimalWeightInfo() {
        Map<String, Integer> map = analysisService.countWeight();
        return Result.success(map);
    }
}