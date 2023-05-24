package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Ccat;
import com.example.petparent.entity.User;

import java.util.List;

public interface ICcatService extends IService<Ccat> {
    
    IPage queryAllList(Ccat ccat);

    boolean deleteCcat(Ccat ccat);

    boolean deleteAllCcat(List<Ccat> ccats);

    boolean addCcat(Ccat ccat);

    boolean changeToWork(Ccat ccat);

    List<User> getSelectMaster();

    IPage queryAllMinecatList(Ccat ccat);

    boolean addMinecat(Ccat ccat);

    IPage queryAllDcatList(Ccat ccat);
}
