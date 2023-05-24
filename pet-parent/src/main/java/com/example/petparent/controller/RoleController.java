package com.example.petparent.controller;


import cn.hutool.core.util.StrUtil;
import com.example.petparent.common.Constants;
import com.example.petparent.common.Result;
import com.example.petparent.entity.Role;
import com.example.petparent.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role) {
        String name = role.getName();
        if (StrUtil.isBlank(name)) {
            return new Result(Constants.CODE_400, "参数不能为空！");
        }
        return new Result(Collections.singleton(roleService.addRole(role)));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllList")
    public Result findPage(@RequestBody Role role) {
        return new Result(roleService.queryAllList(role),null);
    }

    @ApiOperation(value = "删除当前角色")
    @PostMapping(value = "/deleteRole")
    public Result deleteUser(@RequestBody  Role role) {
        Result result = new Result();
        boolean resultLine = roleService.deleteRole(role.getId());
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
    @PostMapping(value = "/deleteAllRole")
    public Result deleteAllRole(@RequestBody  List<Role> roles) {
        Result result = new Result();
        boolean resultLine = roleService.deleteAllRole(roles);
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    @ApiOperation(value = "获取归属父目录")
    @PostMapping(value = "/getSelectRole")
    public Result getSelectRole() {
        Result getSelectRole = new Result(roleService.getSelectRole());
        return getSelectRole;
    }

}

