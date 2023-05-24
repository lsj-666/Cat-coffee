package com.example.petparent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Icon;
import com.example.petparent.entity.RoleMenu;

import java.util.List;

public interface IRoleMenuService extends IService<RoleMenu> {


    List<String> getRoleMenu(String roleId,List<String> menuIds);

    void insertRoleMenu(String roleId, List<String> menuIds);
}
