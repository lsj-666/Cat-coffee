package com.example.petparent.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petparent.common.Result;
import com.example.petparent.entity.Files;
import com.example.petparent.mapper.FileMapper;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.service.IFileService;
import com.example.petparent.utils.SeqGenerateUtil;
import com.example.petparent.utils.ZipUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Resource
    UserMapper userMapper;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IFileService iFileService;
    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":9090/file/" + fileUUID;

            // 存储数据库
            Files saveFile = new Files();
            saveFile.setId(SeqGenerateUtil.seqGenerate().toString());
            saveFile.setName(originalFilename);
            saveFile.setType(type);
            saveFile.setSize(size/1024); // 单位 kb
            saveFile.setUrl(url);
            saveFile.setMd5(md5);
            saveFile.setIsDelete("1");
            fileMapper.insert(saveFile);
        }

        return url;
    }

    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadPage")
    public String uploadPage(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":9090/file/" + fileUUID;

            // 存储数据库
            Files saveFile = new Files();
            saveFile.setId(SeqGenerateUtil.seqGenerate().toString());
            saveFile.setName(originalFilename);
            saveFile.setType(type);
            saveFile.setSize(size/1024); // 单位 kb
            saveFile.setUrl(url);
            saveFile.setMd5(md5);
            fileMapper.insert(saveFile);
        }

        return url;
    }

    /**
     * 文件下载接口   http://localhost:9090/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 文件下载接口   http://localhost:9090/file/{fileUUID}
     * @param files
     * @param response
     * @throws IOException
     */
    @PostMapping("/downloadAll")
    public Result downloadAll(@RequestBody List<Files> files, HttpServletResponse response) throws IOException {
        //临时文件存储位置
//        String filePath = request.getServletContext().getRealPath("/") + "file" + File.separator;
//        String zipPath = request.getServletContext().getRealPath("/") + "zip" + File.separator;
        List<File> fileList = new ArrayList<>();
        FileInputStream inputStream = null;
        List<String> judgeRepeatName = new ArrayList<>();
        try {
            files.forEach(e -> {
                //判断下载的文件是否重名
//                if (!judgeRepeatName.isEmpty()){
//                    for (String getName : judgeRepeatName){
//                        if (e.getName().equals(getName)){
//                            String suffix = e.getName().substring(e.getName().lastIndexOf("."), e.getName().length());
//                            e.setName(e.getName().substring(0,e.getName().indexOf("."))+SeqGenerateUtil.seqGenerate()+suffix);
//                        }
//                    }
//                }
//                judgeRepeatName.add(e.getName());
                fileList.add(new File(fileUploadPath + e.getUrl().substring(e.getUrl().lastIndexOf("/")+1)));
//                fileList.add(new File(fileUploadPath + e.getName()));
            });
            //创建压缩文件
            File zipFile = new File(fileUploadPath + "文件.zip");
            if (zipFile.getParentFile() != null && !zipFile.getParentFile().exists()) {
                boolean mkdirsResult = zipFile.getParentFile().mkdirs();
                if (!mkdirsResult) {
                    throw new RuntimeException("创建目录失败");
                }
            }
            FileOutputStream fos = new FileOutputStream(zipFile);
            //注入压缩包
            ZipUtils.toZip(fileList, fos);
            fos.close();



//            //拿到文件输入流
//            inputStream = new FileInputStream(zipFile);
//            //重置response内容
//            response.reset();
//            //给httpheader添加下载附件
//            response.setCharacterEncoding("UTF-8");
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFile.getName(), "utf-8"));
//            response.addHeader("Content-Length", "" + zipFile.length());
//            response.setContentType("application/octet-stream");
//            // 设置输出流的格式
//            ServletOutputStream out = response.getOutputStream();
//            byte[] bytes = FileUtil.readBytes(zipFile);
//            out.write(bytes);
//            out.flush();
//            out.close();
//            //输出压缩包给浏览器
//            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            throw e;
        } finally {
            Result result = new Result();
            String path = "http://localhost:9090/file/文件.zip" ;
            result.setObject(path);
            return result;
        }
    }


    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllList")
    public Result findPage(@RequestBody Files files) {
        return new Result(iFileService.queryAllList(files),null);
    }

    @ApiOperation(value = "删除文件")
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestBody Files files) {
        return new Result(Collections.singleton(iFileService.deleteFile(files)));
    }

    @ApiOperation(value = "批量删除文件")
    @PostMapping("/deleteAllFile")
    public Result deleteAllFile(@RequestBody List<Files> files) {
        return new Result(Collections.singleton(iFileService.deleteAllFile(files)));
    }

    @ApiOperation(value = "批量删除文件")
    @PostMapping("/modifyFile")
    public Result modifyFile(@RequestBody Files files) {
        return new Result(Collections.singleton(iFileService.modifyFile(files)));
    }

    @ApiOperation(value = "获取文件类下拉框")
    @PostMapping("/selectFileType")
    public Result selectFileType() {
        return new Result(iFileService.selectFileType());
    }



}
