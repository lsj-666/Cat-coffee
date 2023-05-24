package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Workcat;
import com.example.petparent.mapper.BcatMapper;
import com.example.petparent.mapper.WorkcatMapper;
import com.example.petparent.service.IBcatService;
import com.example.petparent.utils.SeqGenerateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BcatServiceImpl extends ServiceImpl<BcatMapper, Bcat> implements IBcatService {


    @Resource
    BcatMapper bcatMapper;

    @Resource
    WorkcatMapper workMapper;

    @Override
    public IPage queryAllList(Bcat bcat) {

        IPage iPage;
        int pageNum = Math.toIntExact(bcat.getPageNum());
        Page bcatPage = new Page(pageNum, bcat.getPageSize());
        iPage = bcatMapper.selectBcatPage(bcatPage, bcat);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            bcatPage = new Page(pageNum, bcat.getPageSize());
            iPage = bcatMapper.selectBcatPage(bcatPage, bcat);
        }

//        IPage iPage;
//        int pageNum = Math.toIntExact(bcat.getPageNum());
//        IPage bcatPage = new Page(pageNum, bcat.getPageSize());
//        LambdaQueryWrapper<Bcat> queryWrapper = new LambdaQueryWrapper<Bcat>();
//        queryWrapper.eq(StringUtils.isNotEmpty(bcat.getName()), Bcat::getName, bcat.getName());
//        queryWrapper.eq(StringUtils.isNotEmpty(bcat.getSex()), Bcat::getSex, bcat.getSex());
//        queryWrapper.eq(StringUtils.isNotEmpty(bcat.getIsWork()), Bcat::getIsWork, bcat.getIsWork());
//        iPage = bcatMapper.selectPage(bcatPage, queryWrapper);
//        if (iPage.getRecords().size() == 0) {
//            pageNum = 1;
//            IPage bcatPageNew = new Page(pageNum, bcat.getPageSize());
//            iPage = bcatMapper.selectPage(bcatPageNew, queryWrapper);
//        }
        return iPage;
    }

    @Override
    public boolean deleteBcat(Bcat bcat) {
        return bcatMapper.deleteById(bcat.getId()) > 0;
    }

    @Override
    public boolean deleteAllBcat(List<Bcat> bcats) {
        ArrayList<String> list = new ArrayList<>();
        for (Bcat bcat : bcats) {
            list.add(bcat.getId());
        }
        return bcatMapper.deleteBatchIds(list) > 0;
    }

    @Override
    public boolean addBcat(Bcat bcat) {
        if (StringUtils.isEmpty(bcat.getId())) {
            bcat.setId(SeqGenerateUtil.seqGenerate().toString());
            return bcatMapper.insert(bcat) > 0;
        } else {
            return bcatMapper.updateById(bcat) > 0;
        }
    }

    @Override
    public boolean changeToWork(Bcat bcat) {
        Workcat workcat = new Workcat();
        workcat.setId(bcat.getId());
        workcat.setName(bcat.getName());
        workcat.setSex(bcat.getSex());
        workcat.setPicture(bcat.getPicture());
        workcat.setHabit(bcat.getHabit());
        workcat.setHobby(bcat.getHobby());
        bcat.setIsWork("1");
        workcat.setIsWork(bcat.getIsWork());
//workMapper.insert(workcat) > 0 &&
        return bcatMapper.updateById(bcat)>0;
    }
}
