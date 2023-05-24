package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Workcat;
import com.example.petparent.mapper.BcatMapper;
import com.example.petparent.mapper.CcatMapper;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.mapper.WorkcatMapper;
import com.example.petparent.service.IWorkcatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkcatServiceImpl extends ServiceImpl<WorkcatMapper, Workcat> implements IWorkcatService {

    @Resource
    CcatMapper ccatMapper;

    @Resource
    BcatMapper bcatMapper;

    @Resource
    WorkcatMapper workMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public IPage queryAllList(Workcat workcat) {
        IPage iPage;
        int pageNum = Math.toIntExact(workcat.getPageNum());
        Page workcatPage = new Page(pageNum, workcat.getPageSize());
        iPage = workMapper.selectWorkcatPage(workcatPage, workcat);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            workcatPage = new Page(pageNum, workcat.getPageSize());
            iPage = workMapper.selectWorkcatPage(workcatPage, workcat);
        }
        return iPage;
    }

    @Override
    public boolean deleteWorkcat(Workcat workcat) {
        return false;
    }

    @Override
    public boolean goHome(List<Workcat> workcats) {
        ArrayList<String> list = new ArrayList<>();
        for (Workcat workcat : workcats) {
            list.add(workcat.getId());
        }
//        workMapper.goBHome(list);
        workMapper.goCHome(list);
        return true;
    }

    @Override
    public boolean addWorkcat(Workcat workcat) {
        return false;
    }

    @Override
    public Object changeToRest(Workcat workcat) {

//        LambdaQueryWrapper<Bcat> bwrapper = new QueryWrapper<Bcat>().lambda();
//        bwrapper.eq(Bcat::getId, workcat.getId());
//        List<Bcat> bbatList = bcatMapper.selectList(bwrapper);
//
//        LambdaQueryWrapper<Ccat> cwrapper = new QueryWrapper<Ccat>().lambda();
//        cwrapper.eq(Ccat::getId, workcat.getId());
//        List<Ccat> cbatList = ccatMapper.selectList(cwrapper);

        return workMapper.changeCToRest(workcat.getId());
    }

    @Override
    public boolean backHome() {
//        workMapper.backBHome();
        workMapper.backCHome();
        return true;
    }
}
