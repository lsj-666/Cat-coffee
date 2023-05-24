package com.example.petparent.controller;

import com.example.petparent.common.Result;
import com.example.petparent.entity.Icon;
import com.example.petparent.service.IIconService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/icon")
public class IconController {

    @Resource
    private IIconService iconService;

    @ApiOperation(value = "获取归属父目录")
    @PostMapping(value = "/getIconSelect")
    public Result getIconSelect() {
        List<Icon> getIcon = iconService.getIcon();
        Result icon = new Result(getIcon);
        return icon;
    }


}
