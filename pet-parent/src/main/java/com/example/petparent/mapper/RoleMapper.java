package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Role;
import com.example.petparent.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> selectRolePage(Page<Role> rolePage, @Param("role") Role role);

    @Select("select id from sys_role where flag = #{flag}")
    String selectByFlag(@Param("flag") String flag);
}
