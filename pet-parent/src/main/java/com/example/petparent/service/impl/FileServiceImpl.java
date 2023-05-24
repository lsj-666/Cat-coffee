package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Files;
import com.example.petparent.mapper.FileMapper;
import com.example.petparent.service.IFileService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public IPage queryAllList(Files files) {
        IPage iPage;
        int pageNum = Math.toIntExact(files.getPageNum());
        IPage filePage = new Page(pageNum, files.getPageSize());
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.like(StringUtils.isNotEmpty(files.getName()), Files::getName, files.getName())
                .eq(StringUtils.isNotEmpty(files.getType()), Files::getType, files.getType())
                .eq(Files::getIsDelete, "0");
        iPage = fileMapper.selectPage(filePage, filesLambdaQueryWrapper);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            IPage filePageNew = new Page(pageNum, files.getPageSize());
            iPage = fileMapper.selectPage(filePageNew, filesLambdaQueryWrapper);
        }
        return iPage;
    }

    @Override
    public boolean deleteFile(Files files) {
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.eq(StringUtils.isNotEmpty(files.getId()), Files::getId, files.getId());
        Files files1 = new Files();
        files1.setIsDelete("1");
        return fileMapper.update(files1, filesLambdaQueryWrapper) > 0;
    }

    @Override
    public boolean deleteAllFile(List<Files> files) {
        ArrayList<String> list = new ArrayList<>();
        for (Files file : files) {
            list.add(file.getId());
        }
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.in(CollectionUtils.isNotEmpty(list), Files::getId, list);
        Files files1 = new Files();
        files1.setIsDelete("1");
        return fileMapper.update(files1, filesLambdaQueryWrapper) > 0;
    }

    @Override
    public boolean modifyFile(Files files) {
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.eq(StringUtils.isNotEmpty(files.getId()), Files::getId, files.getId());
        Files files1 = new Files();
        files1.setName(files.getName());
        return fileMapper.update(files1, filesLambdaQueryWrapper) > 0;
    }

    @Override
    public List<Files> selectFileType() {
        QueryWrapper<Files> filesLambdaQueryWrapper = new QueryWrapper<>();
        filesLambdaQueryWrapper.select("DISTINCT type");
        filesLambdaQueryWrapper.lambda().eq(Files::getIsDelete, "0");
        return fileMapper.selectList(filesLambdaQueryWrapper);
    }
}
