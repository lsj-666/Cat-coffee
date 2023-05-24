package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Icon;
import com.example.petparent.mapper.IconMapper;
import com.example.petparent.service.IIconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IconServiceImpl  extends ServiceImpl<IconMapper, Icon> implements IIconService {

    @Resource
    IconMapper iconMapper;

    @Override
    public List<Icon> getIcon() {
        LambdaQueryWrapper<Icon> wrapper = new QueryWrapper<Icon>().lambda();
        wrapper.orderByAsc(Icon::getName);
        List<Icon> IconList = iconMapper.selectList(wrapper);
        return IconList;
    }
}
