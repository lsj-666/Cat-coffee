package com.example.petparent.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

@Data
@TableName(value = "sys_user")
public class exportDto {
    private String username;
    private String address;
    private String email;
    private String phone;
    private String sex;
    private String avatarUrl;
}
