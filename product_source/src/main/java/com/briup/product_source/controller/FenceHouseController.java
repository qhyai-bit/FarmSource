package com.briup.product_source.controller;

import com.briup.product_source.pojo.ManagerFenceHouse;
import com.briup.product_source.result.Result;
import com.briup.product_source.service.FenceHouseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "栏舍管理模块")
@RestController
@RequestMapping("/fenceHouse")
public class FenceHouseController {
    @Autowired
    private FenceHouseService houseService;

    @ApiOperation("分页多条件查询栏舍信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量",defaultValue = "10", required = true),
            @ApiImplicitParam(name = "fhName", value = "栏舍名称")
    })
    @GetMapping
    public Result queryByConditionsAndPage(Integer pageNum, Integer pageSize, String fhName) {
        PageInfo<ManagerFenceHouse> pageInfo = houseService.findByPage(pageNum, pageSize, fhName);
        return Result.success(pageInfo);
    }

    @ApiOperation("根据栏舍编号查询栏舍信息及栏圈信息接口")
    @ApiImplicitParam(name = "fhId", value = "栏舍编号", required = false, dataType = "string", paramType = "path")
    @GetMapping("/{fhId}")
    public Result queryRelativeDetails(@PathVariable String fhId) {
        return Result.success(houseService.findById(fhId));
    }

    @ApiOperation("新增或修改栏舍接口")
    @PostMapping("/saveOrUpdate")
    public Result reviseFenceHouses(@RequestBody ManagerFenceHouse house) {
        houseService.saveOrUpdate(house);
        return Result.success();
    }

    @ApiOperation("根据栏舍编号删除栏舍信息接口")
    @ApiImplicitParam(name = "fhId", value = "栏舍编号", required = true, dataType = "string", paramType = "path")
    @DeleteMapping("/{fhId}")
    public Result removeById(@PathVariable String fhId) {
        houseService.removeById(fhId);
        return Result.success();
    }

    @ApiOperation("批量删除栏舍接口")
    @DeleteMapping("deleteByIdAll")
    public Result deleteByIdAll(@RequestBody List<String> ids) {
        System.out.println("ids: " + ids);
        houseService.removeBatch(ids);
        return Result.success();
    }

}