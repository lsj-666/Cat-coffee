package com.example.petparent.controller;

import com.example.petparent.common.Result;
import com.example.petparent.entity.Ccat;
import com.example.petparent.service.ICcatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ccat")
public class CcatController {
    @Resource
    private ICcatService ccatService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllCcatList")
    public Result findPage(@RequestBody Ccat ccat) {
        ccat.setFlag("1");
        return new Result(ccatService.queryAllList(ccat),null);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllMinecatList")
    public Result queryAllMinecatList(@RequestBody Ccat ccat) {
        return new Result(ccatService.queryAllMinecatList(ccat),null);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllDcatList")
    public Result queryAllDcatList(@RequestBody Ccat ccat) {
        return new Result(ccatService.queryAllDcatList(ccat),null);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/deleteCcat")
    public Result deleteBcat(@RequestBody Ccat ccat) {
        return new Result(Collections.singleton(ccatService.deleteCcat(ccat)));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping("/deleteAllCcat")
    public Result deleteAllBcat(@RequestBody List<Ccat> ccats) {
        return new Result(Collections.singleton(ccatService.deleteAllCcat(ccats)));
    }

    @ApiOperation(value = "修改文件")
    @PostMapping("/addCcat")
    public Result addCcat(@RequestBody Ccat ccat) {
        ccat.setFlag("1");
        return new Result(Collections.singleton(ccatService.addCcat(ccat)));
    }

    @ApiOperation(value = "修改文件")
    @PostMapping("/addMinecat")
    public Result addMinecat(@RequestBody Ccat ccat) {
        return new Result(Collections.singleton(ccatService.addMinecat(ccat)));
    }

    @ApiOperation(value = "派去打工")
    @PostMapping("/changeToWork")
    public Result changeToWork(@RequestBody Ccat ccat) {
        return new Result(Collections.singleton(ccatService.changeToWork(ccat)));
    }

    @ApiOperation(value = "获取铲屎官下拉框")
    @PostMapping(value = "/getSelectMaster")
    public Result getSelectMaster() {
        Result getSelectMaster = new Result(ccatService.getSelectMaster());
        return getSelectMaster;
    }

}
