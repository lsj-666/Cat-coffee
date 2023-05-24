package com.example.petparent.controller;


import cn.hutool.core.util.StrUtil;
import com.example.petparent.common.Constants;
import com.example.petparent.common.Result;
import com.example.petparent.entity.Menu;
import com.example.petparent.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody Menu menu) {
        String name = menu.getName();
        if (StrUtil.isBlank(name)) {
            return new Result(Constants.CODE_400, "参数不能为空！");
        }
        return new Result(Collections.singleton(menuService.addMenu(menu)));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllList")
    public Result findPage(@RequestBody Menu menu) {
        return new Result(menuService.queryAllList(menu),null);
    }

    @ApiOperation(value = "删除当前角色")
    @PostMapping(value = "/deleteMenu")
    public Result deleteUser(@RequestBody  Menu menu) {
        Result result = new Result();
        boolean resultLine = menuService.deleteMenu(menu.getId());
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    @ApiOperation(value = "批量删除当前角色")
    @PostMapping(value = "/deleteAllMenu")
    public Result deleteAllMenu(@RequestBody List<Menu> menus) {
        Result result = new Result();
        boolean resultLine = menuService.deleteAllMenu(menus);
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    @ApiOperation(value = "获取菜单目录树")
    @RequestMapping(value = "/getMenuTree", method = RequestMethod.GET)
    public Result getMenuTree() {
        List<Menu> csgMenuList = menuService.getMenuTree();
        Result csgMenuResult = new Result(csgMenuList);
        return csgMenuResult;
    }

    @ApiOperation(value = "获取归属父目录")
    @PostMapping(value = "/getPidSelect")
    public Result getPidSelect(@RequestBody Menu menu) {
        List<Menu> getPidSelect = menuService.getPidSelect(menu.getId());
        Result pidSelectResult = new Result(getPidSelect);
        return pidSelectResult;
    }


}

