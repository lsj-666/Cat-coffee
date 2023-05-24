package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Menu;
import com.example.petparent.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MenuMapper extends BaseMapper<Menu> {

    IPage<Menu> selectMenuPage(Page<Menu> menuPage, @Param("menu") Menu menu);

}
