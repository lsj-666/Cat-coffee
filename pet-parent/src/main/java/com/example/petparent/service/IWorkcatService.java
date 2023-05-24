package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Ccat;
import com.example.petparent.entity.Workcat;

import java.util.List;

public interface IWorkcatService extends IService<Workcat> {
    
    IPage queryAllList(Workcat workcat);

    boolean deleteWorkcat(Workcat workcat);

    boolean goHome(List<Workcat> workcats);

    boolean addWorkcat(Workcat workcat);

    Object changeToRest(Workcat workcat);

    boolean backHome();
}
