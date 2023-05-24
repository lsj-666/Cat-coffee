package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Meal;
import com.example.petparent.mapper.MealMapper;
import com.example.petparent.service.IMealService;
import com.example.petparent.utils.SeqGenerateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MealServiceImpl extends ServiceImpl<MealMapper, Meal> implements IMealService {

    @Resource
    MealMapper mealMapper;

    @Override
    public IPage queryAllList(Meal meal) {
//        meal.setStartTime(getEndOfDay(meal.getTime()));
//        meal.setEndTime(getStartOfDay(meal.getTime()));

        if (meal.getTime() != null){
            meal.setStartTime(meal.getTime().toString().substring(0,11)+"00:00:00");
            meal.setEndTime(meal.getTime().toString().substring(0,11)+"23:59:59");
        }
        IPage iPage;
        int pageNum = Math.toIntExact(meal.getPageNum());
        Page mealPage = new Page(pageNum, meal.getPageSize());
        iPage = mealMapper.selectMealPage(mealPage, meal);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            mealPage = new Page(pageNum, meal.getPageSize());
            iPage = mealMapper.selectMealPage(mealPage, meal);
        }
        return iPage;
    }

    @Override
    public boolean addMealList(Meal meal) {
        String price = "0";
        switch (meal.getMeal()) {
            case "1":
                price = "48";
                break;
            case "2":
                price = "58";
                break;
            case "3":
                price = "68";
                break;
            case "4":
                price = "78";
                break;
            default:
                price = "0";
                break;
        }
        meal.setPrice(price);
        if (StringUtils.isEmpty(meal.getId())) {
            meal.setId(SeqGenerateUtil.seqGenerate().toString());
            return mealMapper.insert(meal) > 0;
        } else {
            return mealMapper.updateById(meal) > 0;
        }
    }

    @Override
    public boolean delMealList(Meal meal) {
        return mealMapper.deleteById(meal.getId()) > 0;
    }

    @Override
    public boolean delAllMealList(List<Meal> meals) {
        List<String> ids = new ArrayList<>();
        meals.forEach(m -> {
            ids.add(m.getId());
        });
        return mealMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Meal selectMealInfo() {
        return mealMapper.selectOneMeal();
    }

    @Override
    public IPage queryMyMealList(Meal meal) {

        IPage iPage;
        int pageNum = Math.toIntExact(meal.getPageNum());
        Page mealPage = new Page(pageNum, meal.getPageSize());
        LambdaQueryWrapper<Meal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(meal.getUserId()),Meal::getUserId,meal.getUserId());
        iPage = mealMapper.selectPage(mealPage, queryWrapper);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            mealPage = new Page(pageNum, meal.getPageSize());
            iPage = mealMapper.selectPage(mealPage, queryWrapper);
        }
        return iPage;
    }

    // 获得某天最大时间 2022-12-09 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2022-12-09 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
