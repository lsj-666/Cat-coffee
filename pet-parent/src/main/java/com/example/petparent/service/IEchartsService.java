package com.example.petparent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Echarts;

public interface IEchartsService extends IService<Echarts> {

    int getAllCats();

    int getAtStoreCats();

    int getWorkCats();

    int getCustomers();
}
