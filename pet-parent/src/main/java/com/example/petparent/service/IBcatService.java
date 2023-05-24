package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.petparent.entity.Bcat;
import com.example.petparent.entity.Menu;

import java.util.List;

public interface IBcatService extends IService<Bcat> {

    IPage queryAllList(Bcat bcat);

    boolean deleteBcat(Bcat bcat);

    boolean deleteAllBcat(List<Bcat> bcats);

    boolean addBcat(Bcat bcat);

    boolean changeToWork(Bcat bcat);
}
