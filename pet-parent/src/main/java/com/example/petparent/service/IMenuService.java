package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Menu;
import com.example.petparent.entity.Role;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    boolean addMenu(Menu menu);

    IPage queryAllList(Menu menu);

    boolean deleteMenu(String id);

    boolean deleteAllMenu(List<Menu> menus);

    /**
     * 获取Menu树
     *
     * @return
     */
    List<Menu> getMenuTree();

    List<Menu> getPidSelect(String id);

}
