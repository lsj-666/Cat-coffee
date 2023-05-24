package com.example.petparent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("sys_icon")
@ApiModel(value = "Icon对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Icon {

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("内容")
    @TableField("value")
    private String value;

    @ApiModelProperty("类型")
    @TableField("type")
    private String type;

}
