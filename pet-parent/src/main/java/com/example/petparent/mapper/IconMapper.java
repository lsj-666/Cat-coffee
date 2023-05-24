package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Icon;
import com.example.petparent.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface IconMapper extends BaseMapper<Icon> {


}
