package com.example.petparent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@TableName("store_meal")
@ApiModel(value = "meal对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty("名称")
    @TableField("userId")
    private String userId;

    @ApiModelProperty("名称")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("预约时间")
    @TableField("time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp time;

    @ApiModelProperty("套餐")
    @TableField("meal")
    private String meal;

    @ApiModelProperty("price")
    @TableField("price")
    private String price;

    @ApiModelProperty("预约时间")
    @TableField(exist = false)
    private String startTime;

    @ApiModelProperty("预约时间")
    @TableField(exist = false)
    private String endTime;

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
