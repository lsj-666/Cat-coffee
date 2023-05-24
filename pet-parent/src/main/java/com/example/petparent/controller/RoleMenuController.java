package com.example.petparent.controller;

import com.example.petparent.common.Result;
import com.example.petparent.controller.dto.RoleMenuDTO;
import com.example.petparent.service.IRoleMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService iRoleMenuService;

    @ApiOperation(value = "获取权限目录")
    @PostMapping(value = "/getRoleMenu")
    public Result getRoleMenu(@RequestBody RoleMenuDTO roleMenuDTO) {
        String roleId = roleMenuDTO.getRoleId();
        List<String> menuIds = roleMenuDTO.getMenuIds();
        List<String> getRoleMenu = iRoleMenuService.getRoleMenu(roleId, menuIds);
        Result result = new Result(getRoleMenu);
        return result;
    }


    @ApiOperation(value = "修改权限目录")
    @PostMapping(value = "/insertRoleMenu")
    public Result insertRoleMenu(@RequestBody RoleMenuDTO roleMenuDTO) {
        String roleId = roleMenuDTO.getRoleId();
        List<String> menuIds = roleMenuDTO.getMenuIds();
        iRoleMenuService.insertRoleMenu(roleId, menuIds);
        Result result = new Result();
        result.setReturnCode(Result.OK);
        return result;
    }


}
