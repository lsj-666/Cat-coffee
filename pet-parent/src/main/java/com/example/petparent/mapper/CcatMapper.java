package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Ccat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CcatMapper extends BaseMapper<Ccat> {


    IPage<Ccat> selectCcatPage(Page<Ccat> ccatPage, @Param("ccat") Ccat ccat);

    IPage<Ccat> selectMinecatPage(Page<Ccat> ccatPage, @Param("ccat") Ccat ccat);

    IPage<Ccat> selectDcatPage(Page<Ccat> ccatPage, @Param("ccat") Ccat ccat);

    int getAllCats();

    int getAtStoreCats();

    int getWorkCats();

    List<Ccat> getPieExample();
}
