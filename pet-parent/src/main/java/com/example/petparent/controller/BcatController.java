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
@RequestMapping("/bcat")
public class BcatController {
    @Resource
    private ICcatService ccatService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllBcatList")
    public Result findPage(@RequestBody Ccat ccat) {
        ccat.setFlag("0");
        return new Result(ccatService.queryAllList(ccat),null);
    }


    @ApiOperation(value = "删除")
    @PostMapping("/deleteCcat")
    public Result deleteCcat(@RequestBody Ccat ccat) {
        return new Result(Collections.singleton(ccatService.deleteCcat(ccat)));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping("/deleteAllCcat")
    public Result deleteAllCcat(@RequestBody List<Ccat> ccats) {
        return new Result(Collections.singleton(ccatService.deleteAllCcat(ccats)));
    }

    @ApiOperation(value = "修改文件")
    @PostMapping("/addBcat")
    public Result addCcat(@RequestBody Ccat ccat) {
        ccat.setFlag("0");
        ccat.setMaster("-1");
        ccat.setStatus("-1");
        return new Result(Collections.singleton(ccatService.addCcat(ccat)));
    }

    @ApiOperation(value = "派去打工")
    @PostMapping("/changeToWork")
    public Result changeToWork(@RequestBody Ccat ccat) {
        return new Result(Collections.singleton(ccatService.changeToWork(ccat)));
    }

}
