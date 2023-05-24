package com.example.petparent.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.petparent.common.Constants;
import com.example.petparent.common.Result;
import com.example.petparent.controller.dto.UserDTO;
import com.example.petparent.controller.dto.exportDto;
import com.example.petparent.entity.User;
import com.example.petparent.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        Result result=new Result();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            result.setReturnCode(Constants.CODE_400);
            result.setReturnMessage("用户名或密码不能为空");
            return result;
        }
        UserDTO dto = userService.login(userDTO);
        result.setReturnCode(Constants.CODE_200);
        result.setObject(dto);
        return result;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        Result result=new Result();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return new Result(Constants.CODE_400, "参数错误");
        }
        User register = userService.register(userDTO);
        result.setObject(register);
        return result;
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.isBlank(username)) {
            return new Result(Constants.CODE_400, "用户名不能为空！");
        }
        return new Result(Collections.singleton(userService.addUser(user)));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/modifyPassword")
    public Result modifyPassword(@RequestBody User user) {
        String password = user.getPassword();
        if (StrUtil.isBlank(password)) {
            return new Result(Constants.CODE_400, "密码不能为空！");
        }
        return new Result(Collections.singleton(userService.modifyPassword(user)));
    }

    @ApiOperation(value = "查找用户名")
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        Result result=new Result();
        User user = userService.getOne(username);
        result.setObject(user);
        return result;
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryAllList")
    public Result findPage(@RequestBody User user) {
        return new Result(userService.queryAllList(user),null);
    }

    @ApiOperation(value = "删除当前用户")
    @PostMapping(value = "/deleteUser")
    public Result deleteUser(@RequestBody User user) {
        Result result = new Result();
        boolean resultLine = userService.deleteUser(user.getId());
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    @ApiOperation(value = "删除当前用户")
    @PostMapping(value = "/resetPassword")
    public Result resetPassword(@RequestBody User user) {
        Result result = new Result();
        boolean resultLine = userService.resetPassword(user);
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    @ApiOperation(value = "批量删除当前用户")
    @PostMapping(value = "/deleteAllUser")
    public Result deleteAllUser(@RequestBody List<User> users) {
        Result result = new Result();
        boolean resultLine = userService.deleteAllUser(users);
        if (resultLine) {
            result.setReturnCode(Constants.CODE_200);
            result.setReturnMessage("删除成功");
        } else {
            result.setReturnCode(Constants.CODE_500);
            result.setReturnMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出接口
     */
    @ApiOperation(value = "导出接口")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<exportDto> list = userService.listUsers();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("sex", "姓别");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.setOnlyAlias(true);
        writer.autoSizeColumnAll();
        writer.setColumnWidth(1,12);
        writer.setColumnWidth(2,20);
        writer.setColumnWidth(3,20);
        writer.setColumnWidth(5,70);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setAddress(row.get(1).toString());
            user.setEmail(row.get(2).toString());
            user.setPhone(row.get(3).toString());
            user.setSex(row.get(4).toString());
            user.setAvatarUrl(row.get(5).toString());
            users.add(user);
        }
        userService.addUsers(users);
        return new Result(Collections.singleton(true));
    }

}
