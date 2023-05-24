package com.example.petparent.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuDTO {

    private String roleId;
    private List<String> menuIds;
}
