package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.controller.dto.exportDto;
import com.example.petparent.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectUserPage(Page<User> userPage, @Param("user") User user);

    boolean deleteByUserId(String id);

    int getCustomers();

    List<exportDto> listUsers();
}
