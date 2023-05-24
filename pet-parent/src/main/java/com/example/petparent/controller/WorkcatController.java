package com.example.petparent.controller;

import com.example.petparent.common.Result;
import com.example.petparent.entity.Workcat;
import com.example.petparent.service.IWorkcatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/workcat")
public class WorkcatController {
    @Resource
    private IWorkcatService workcatService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllWorkcatList")
    public Result findPage(@RequestBody Workcat workcat) {
        return new Result(workcatService.queryAllList(workcat),null);
    }


    @ApiOperation(value = "删除")
    @PostMapping("/deleteWorkcat")
    public Result deleteBcat(@RequestBody Workcat workcat) {
        return new Result(Collections.singleton(workcatService.deleteWorkcat(workcat)));
    }

    @ApiOperation(value = "下班")
    @PostMapping("/goHome")
    public Result goHome(@RequestBody List<Workcat> workcats) {
        return new Result(Collections.singleton(workcatService.goHome(workcats)));
    }

    @ApiOperation(value = "下班")
    @PostMapping("/backHome")
    public Result backHome() {
        return new Result(Collections.singleton(workcatService.backHome()));
    }

    @ApiOperation(value = "修改文件")
    @PostMapping("/addWorkcat")
    public Result addBcat(@RequestBody Workcat workcat) {
        return new Result(Collections.singleton(workcatService.addWorkcat(workcat)));
    }

    @ApiOperation(value = "派去打工")
    @PostMapping("/changeToRest")
    public Result changeToRest(@RequestBody Workcat workcat) {
        return new Result(Collections.singleton(workcatService.changeToRest(workcat)));
    }

}
