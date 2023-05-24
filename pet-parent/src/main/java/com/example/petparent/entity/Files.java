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
@TableName("sys_files")
@ApiModel(value = "files对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private String id;

    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private String isDelete;
    private boolean enable;

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
