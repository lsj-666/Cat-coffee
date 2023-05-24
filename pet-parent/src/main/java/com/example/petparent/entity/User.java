package com.example.petparent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

@Data
@TableName(value = "sys_user")
public class User {
    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;

    /**
     * username
     */
    @TableField("username")
    private String username;
    @Ignore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String sex;
    private String status;

    @TableField(exist = false)
    private String name;

    @TableField(value = "avatar_url")  // 指定数据库的字段名称
    private String avatarUrl;

    /**
     * 页码
     */
    @TableField(exist = false)
    private Long pageNum;

    /**
     * 页面容量
     */
    @TableField(exist = false)
    private Long pageSize;
}
