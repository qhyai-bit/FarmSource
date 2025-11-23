package com.briup.product_source.controller;

import com.briup.product_source.pojo.ManagerAnimal;
import com.briup.product_source.pojo.ext.ManagerAnimalExt;
import com.briup.product_source.result.Result;
import com.briup.product_source.service.ManagerAnimalService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "动物管理模块")
@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private ManagerAnimalService animalService;

    @ApiOperation("分页多条件查询动物信息(含栏圈名称、栏舍名称及批次信息)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "aHeathy", value = "健康状态", paramType = "query"),
            @ApiImplicitParam(name = "aGender", value = "动物性别", paramType = "query")
    })
    @GetMapping
    public Result findByPage(Integer pageNum,
                             Integer pageSize,
                             String aHealthy,
                             String aGender) {
        PageInfo<ManagerAnimalExt> pageInfo = animalService.findByPage(pageNum, pageSize, aHealthy, aGender);
        return Result.success(pageInfo);
    }

    @ApiOperation("新增或修改动物接口")
    @PostMapping("/saveOrUpdate")
    public Result reviseAnimalInfo(@RequestBody ManagerAnimal animal) {
        animalService.saveOrUpdate(animal);
        return Result.success();
    }
}
