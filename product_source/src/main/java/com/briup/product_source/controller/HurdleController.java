package com.briup.product_source.controller;

import com.briup.product_source.pojo.ManagerHurdles;
import com.briup.product_source.pojo.ext.ManagerHurdlesExt;
import com.briup.product_source.result.Result;
import com.briup.product_source.service.HurdlesService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "栏圈管理模块")
@RestController
@RequestMapping("/hurdle")
public class HurdleController {
    @Autowired
    private HurdlesService hurdlesService;

    @ApiOperation("查询所有栏圈的最大容量")
    @GetMapping("/queryAllMax")
    public Result queryAllMax() {
        List<Integer> list = hurdlesService.findAllMax();
        return Result.success(list);
    }

    @ApiOperation("分页多条件查询栏舍信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", defaultValue = "1", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",defaultValue = "10", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "hName", value = "栏圈名称", required = false, dataType = "string"),
            @ApiImplicitParam(name = "hMax", value = "栏圈容量", required = false, dataType = "integer"),
            @ApiImplicitParam(name = "fhName", value = "所属栏舍", required = false, dataType = "string"),
            @ApiImplicitParam(name = "hEnable", value = "是否可用", required = false, dataType = "string")
    })
    @GetMapping
    public Result queryByConditionsAndPage(Integer pageNum, Integer pageSize,
                                           String hName, Integer hMax,
                                           String fhName, String hEnable) {
        PageInfo<ManagerHurdlesExt> pageInfo = hurdlesService.findByPage(pageNum, pageSize, hName,
                                                                            hMax, fhName, hEnable);
        return Result.success(pageInfo);
    }

    @ApiOperation("启用/禁用栏圈接口")
    @PutMapping("/{hId}/{hEnable}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hId", value = "栏圈编号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "hEnable", value = "栏圈状态", required = true, dataType = "string")
    })
    public Result changeStatus(@PathVariable String hId,
                               @PathVariable String hEnable) {
        hurdlesService.modifyStatus(hId, hEnable);
        return Result.success();
    }

    @ApiOperation("批量禁用/启用栏圈")
    @PutMapping
    public Result changeStatusBatch(@RequestBody List<Map<String,String>> list){
        hurdlesService.modifyStatusBatch(list);
        return Result.success();
    }

    @ApiOperation("查询所有栏圈信息(可用、未满)")
    @GetMapping("/queryAllEnable")
    public Result getAllEnableHurdles() {
        List<ManagerHurdles> list = hurdlesService.findAllEnable();
        return Result.success(list);
    }
}
