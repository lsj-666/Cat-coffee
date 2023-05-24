package com.example.petparent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.petparent.entity.Files;
import com.example.petparent.entity.Role;

import java.util.List;

public interface IFileService {
    IPage queryAllList(Files files);

    boolean deleteFile(Files files);

    boolean deleteAllFile(List<Files> files);

    boolean modifyFile(Files files);

    List<Files> selectFileType();
}
