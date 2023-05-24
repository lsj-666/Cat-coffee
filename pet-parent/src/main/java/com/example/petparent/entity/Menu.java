package com.example.petparent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("路径")
    @TableField("path")
    private String path;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("可用状态")
    @TableField("state")
    private String state;

    @TableField(exist = false)
    private List<Menu> children;

    @ApiModelProperty("父目录id")
    @TableField("pid")
    private String pid;

    @ApiModelProperty("菜单路径")
    @TableField("page_path")
    private String pagePath;

    @ApiModelProperty("菜单排序")
    @TableField("sort")
    private int sort;

//
//    private String pagePath;
//    private String sortNum;

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

    /**
     * 根节点默认展示
     */
    @TableField(exist = false)
    private String alwaysShow;

    @TableField(exist = false)
    private Map meta;



}
