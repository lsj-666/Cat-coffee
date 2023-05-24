package com.example.petparent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Icon;
import com.example.petparent.entity.Menu;

import java.util.List;

public interface IIconService  extends IService<Icon> {

    /**
     * 获取图标下拉框
     * @return
     */
    List<Icon> getIcon();

}
