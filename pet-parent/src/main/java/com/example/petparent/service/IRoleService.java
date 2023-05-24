package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Role;
import com.example.petparent.entity.User;

import java.util.List;

public interface IRoleService extends IService<Role> {

    boolean addRole(Role role);

    IPage queryAllList(Role role);

    boolean deleteRole(String id);

    boolean deleteAllRole(List<Role> roles);

    List<Role> getSelectRole();
}
