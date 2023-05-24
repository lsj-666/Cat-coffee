package com.example.petparent.controller;

import com.example.petparent.common.Result;
import com.example.petparent.entity.Meal;
import com.example.petparent.service.IMealService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/meal")
public class MealController {
    @Resource
    private IMealService mealService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllMealList")
    public Result queryAllMealList(@RequestBody Meal meal) {

        return new Result(mealService.queryAllList(meal),null);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryMyMealList")
    public Result queryMyMealList(@RequestBody Meal meal) {

        return new Result(mealService.queryMyMealList(meal),null);
    }

    @ApiOperation(value = "增加或修改预约信息")
    @PostMapping("/addMealList")
    public Result addMealList(@RequestBody Meal meal) {
        return new Result(Collections.singleton(mealService.addMealList(meal)));
    }

    @ApiOperation(value = "删除预约信息")
    @PostMapping("/delMealList")
    public Result delMealList(@RequestBody Meal meal) {
        return new Result(Collections.singleton(mealService.delMealList(meal)));
    }

    @ApiOperation(value = "批量删除预约信息")
    @PostMapping("/delAllMealList")
    public Result delAllMealList(@RequestBody List<Meal> meals) {
        return new Result(Collections.singleton(mealService.delAllMealList(meals)));
    }

    @ApiOperation(value = "获取预约信息")
    @PostMapping("/selectMealInfo")
    public Result selectMealInfo() {
        return new Result(Collections.singleton(mealService.selectMealInfo()));
    }
}
