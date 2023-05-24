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

import java.sql.Blob;

@Data
@TableName("store_bcat")
@ApiModel(value = "bcat对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bcat {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("头像")
    @TableField("picture")
    private String picture;

    @ApiModelProperty("性格")
    @TableField("habit")
    private String habit;

    @ApiModelProperty("爱好")
    @TableField("hobby")
    private String hobby;

    @ApiModelProperty("年龄")
    @TableField("age")
    private String age;

    @ApiModelProperty("是否打工")
    @TableField("is_work")
    private String isWork;

    @ApiModelProperty("状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("铲屎官")
    @TableField("master")
    private String master;

    @ApiModelProperty("铲屎官Name")
    @TableField(exist = false)
    private String username;

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
