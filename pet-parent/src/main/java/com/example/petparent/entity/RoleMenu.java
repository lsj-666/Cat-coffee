package com.example.petparent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("sys_role_menu")
@ApiModel(value = "role_menu", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu {

    @ApiModelProperty("role_id")
    @TableField(value = "role_id")
    private String roleId;

    @ApiModelProperty("menu_id")
    @TableField(value = "menu_id")
    private String menuId;

}
