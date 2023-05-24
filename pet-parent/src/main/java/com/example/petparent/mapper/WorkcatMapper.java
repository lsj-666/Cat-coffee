package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Ccat;
import com.example.petparent.entity.Workcat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface WorkcatMapper extends BaseMapper<Workcat> {


    IPage<Workcat> selectWorkcatPage(Page<Workcat> workcatPage, @Param("workcat") Workcat workcat);

    boolean changeBToRest(String id);
    boolean changeCToRest(String id);

    boolean goBHome(ArrayList<String> list);
    boolean goCHome(ArrayList<String> list);

    boolean backBHome();
    boolean backCHome();
}
