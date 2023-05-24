package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Ccat;
import com.example.petparent.entity.User;
import com.example.petparent.entity.Workcat;
import com.example.petparent.mapper.CcatMapper;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.mapper.WorkcatMapper;
import com.example.petparent.service.ICcatService;
import com.example.petparent.utils.SeqGenerateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CcatServiceImpl extends ServiceImpl<CcatMapper, Ccat> implements ICcatService {
    @Resource
    CcatMapper ccatMapper;

    @Resource
    WorkcatMapper workMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public IPage queryAllList(Ccat ccat) {
        IPage iPage;
        int pageNum = Math.toIntExact(ccat.getPageNum());
        Page ccatPage = new Page(pageNum, ccat.getPageSize());
        iPage = ccatMapper.selectCcatPage(ccatPage, ccat);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            ccatPage = new Page(pageNum, ccat.getPageSize());
            iPage = ccatMapper.selectCcatPage(ccatPage, ccat);
        }
        return iPage;
    }

    @Override
    public boolean deleteCcat(Ccat ccat) {
        return ccatMapper.deleteById(ccat.getId()) > 0;
    }

    @Override
    public boolean deleteAllCcat(List<Ccat> ccats) {
        ArrayList<String> list = new ArrayList<>();
        for (Ccat ccat : ccats) {
            list.add(ccat.getId());
        }
        return ccatMapper.deleteBatchIds(list) > 0;
    }

    @Override
    public boolean addCcat(Ccat ccat) {
        if (StringUtils.isEmpty(ccat.getId())) {
            ccat.setId(SeqGenerateUtil.seqGenerate().toString());
            return ccatMapper.insert(ccat) > 0;
        } else {
            return ccatMapper.updateById(ccat) > 0;
        }
    }

    @Override
    public boolean changeToWork(Ccat ccat) {
        Workcat workcat = new Workcat();
        workcat.setId(ccat.getId());
        workcat.setName(ccat.getName());
        workcat.setSex(ccat.getSex());
        workcat.setPicture(ccat.getPicture());
        workcat.setHabit(ccat.getHabit());
        workcat.setHobby(ccat.getHobby());
        workcat.setStatus(ccat.getStatus());
        workcat.setMaster(ccat.getMaster());
        ccat.setIsWork("1");
        workcat.setIsWork(ccat.getIsWork());
//        workMapper.insert(workcat) > 0 &&
        return ccatMapper.updateById(ccat)>0;
    }

    @Override
    public List<User> getSelectMaster() {
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda();
        wrapper.orderByAsc(User::getId);
        wrapper.eq(User::getStatus,"1");
        List<User> masterList = userMapper.selectList(wrapper);
        return masterList;
    }

    @Override
    public IPage queryAllMinecatList(Ccat ccat) {
        IPage iPage;
        int pageNum = Math.toIntExact(ccat.getPageNum());
        Page ccatPage = new Page(pageNum, ccat.getPageSize());
        iPage = ccatMapper.selectMinecatPage(ccatPage, ccat);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            ccatPage = new Page(pageNum, ccat.getPageSize());
            iPage = ccatMapper.selectMinecatPage(ccatPage, ccat);
        }
        return iPage;
    }

    @Override
    public boolean addMinecat(Ccat ccat) {
        if (StringUtils.isEmpty(ccat.getId())) {
            ccat.setId(SeqGenerateUtil.seqGenerate().toString());
            return ccatMapper.insert(ccat) > 0;
        } else {
            return ccatMapper.updateById(ccat) > 0;
        }
    }

    @Override
    public IPage queryAllDcatList(Ccat ccat) {
        ccat.setStatus("1");
        IPage iPage;
        int pageNum = Math.toIntExact(ccat.getPageNum());
        Page ccatPage = new Page(pageNum, ccat.getPageSize());
        iPage = ccatMapper.selectDcatPage(ccatPage, ccat);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            ccatPage = new Page(pageNum, ccat.getPageSize());
            iPage = ccatMapper.selectDcatPage(ccatPage, ccat);
        }
        return iPage;
    }
}
