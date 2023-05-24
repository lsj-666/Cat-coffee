package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.petparent.controller.dto.UserDTO;
import com.example.petparent.controller.dto.exportDto;
import com.example.petparent.entity.Menu;
import com.example.petparent.entity.User;

import java.util.List;

public interface IUserService {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    User getById(String userId);

    User getOne(String username);

    IPage queryAllList(User user);

    boolean deleteUser(String id);

    boolean resetPassword(User user);

    boolean deleteAllUser(List<User> users);

    boolean addUser(User user);

    boolean modifyPassword(User user);

    List<exportDto> listUsers();

    boolean addUsers(List<User> users);
}
