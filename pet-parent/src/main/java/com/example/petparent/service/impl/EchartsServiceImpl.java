package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Echarts;
import com.example.petparent.mapper.CcatMapper;
import com.example.petparent.mapper.EchartsMapper;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.service.IEchartsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EchartsServiceImpl extends ServiceImpl<EchartsMapper, Echarts> implements IEchartsService {
    @Resource
    CcatMapper ccatMapper;

    @Resource
    UserMapper userMapper;


    @Override
    public int getAllCats() {
        return ccatMapper.getAllCats();
    }

    @Override
    public int getAtStoreCats() {
        return ccatMapper.getAtStoreCats();
    }

    @Override
    public int getWorkCats() {
        return ccatMapper.getWorkCats();
    }

    @Override
    public int getCustomers() {
        return userMapper.getCustomers();
    }
}
