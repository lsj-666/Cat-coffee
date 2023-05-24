package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Meal;

import java.util.List;

public interface IMealService extends IService<Meal> {

    IPage queryAllList(Meal meal);

    boolean addMealList(Meal meal);

    boolean delMealList(Meal meal);

    boolean delAllMealList(List<Meal> meals);

    Meal selectMealInfo();

    IPage queryMyMealList(Meal meal);
}
