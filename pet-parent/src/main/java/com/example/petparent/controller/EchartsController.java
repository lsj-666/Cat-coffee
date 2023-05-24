package com.example.petparent.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.petparent.common.Result;
import com.example.petparent.entity.Ccat;
import com.example.petparent.mapper.CcatMapper;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.service.IEchartsService;
import com.example.petparent.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

//    @Autowired
//    private IUserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    CcatMapper ccatMapper;

    @Resource
    RedisUtil redisUtil;

    @Resource
    IEchartsService iEchartsService;

    @GetMapping("/example")
    public Result get() {
        Result result = new Result();
        Map<String, Object> map = new HashMap<>(16);
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        result.setObject(map);
        return result;
    }

    @GetMapping("/getPieExample")
    public Result getPieExample() {
        List<Map<String,Object>> list = new ArrayList<>();
        Result result = new Result();
        List<Ccat> listCcat = ccatMapper.getPieExample();
        for (Ccat ccat : listCcat){
            Map<String, Object> map = new HashMap<>(16);
            map.put("name",ccat.getName());
            map.put("value",ccat.getCount());
            list.add(map);
        }
        result.setObject(list);
        return result;
    }

//    @GetMapping("/members")
//    public Result members() {
//        List<User> list = userService.list();
//        int q1 = 0; // 第一季度
//        int q2 = 0; // 第二季度
//        int q3 = 0; // 第三季度
//        int q4 = 0; // 第四季度
//        for (User user : list) {
//            Date createTime = user.getCreateTime();
//            Quarter quarter = DateUtil.quarterEnum(createTime);
//            switch (quarter) {
//                case Q1: q1 += 1; break;
//                case Q2: q2 += 1; break;
//                case Q3: q3 += 1; break;
//                case Q4: q4 += 1; break;
//                default: break;
//            }
//        }
//        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
//    }

    @ApiOperation(value = "获取总喵数")
    @PostMapping("/getAllCats")
    public Result getAllCats() {
        Result result = new Result();
        if (redisUtil.get("getAllCats") != null){
            result.setObject(redisUtil.get("getAllCats"));
            redisUtil.expire("getAllCats", 300);
        }else {
            Object number = iEchartsService.getAllCats();
            result.setObject(number);
            redisUtil.set("getAllCats",number);
        }
        return result;
    }

    @ApiOperation(value = "获取在店喵数")
    @PostMapping("/getAtStoreCats")
    public Result getAtStoreCats() {
        Result result = new Result();
        if (redisUtil.get("getAtStoreCats") != null){
            result.setObject(redisUtil.get("getAtStoreCats"));
            redisUtil.expire("getAtStoreCats", 300);
        }else {
            Object number =iEchartsService.getAtStoreCats();
            result.setObject(number);
            redisUtil.set("getAtStoreCats",number);
        }
        return result;
    }

    @ApiOperation(value = "获取上岗喵数")
    @PostMapping("/getWorkCats")
    public Result getWorkCats() {
        Result result = new Result();
        if (redisUtil.get("getWorkCats") != null){
            result.setObject(redisUtil.get("getWorkCats"));
            redisUtil.expire("getWorkCats", 300);
        }else {
            Object number =iEchartsService.getWorkCats();
            result.setObject(number);
            redisUtil.set("getWorkCats",number);
        }
        return result;
    }

    @ApiOperation(value = "获取顾客数量")
    @PostMapping("/getCustomers")
    public Result getCustomers() {
        Result result = new Result();
        if (redisUtil.get("getCustomers") != null){
            result.setObject(redisUtil.get("getCustomers"));
            redisUtil.expire("getCustomers", 300);
        }else {
            Object number =iEchartsService.getCustomers();
            result.setObject(number);
            redisUtil.set("getCustomers",number);
        }
        return result;
    }
}
