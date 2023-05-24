package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petparent.entity.Icon;
import com.example.petparent.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    List<RoleMenu> deleteByRoleId(String roleId);

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<String> selectByRoleId(@Param("roleId")String roleId);
}
