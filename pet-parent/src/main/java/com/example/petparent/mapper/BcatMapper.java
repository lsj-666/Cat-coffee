package com.example.petparent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Ccat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface BcatMapper extends BaseMapper<Bcat> {


    IPage<Bcat> selectBcatPage(Page<Bcat> bcatPage, @Param("bcat") Bcat bcat);
}
