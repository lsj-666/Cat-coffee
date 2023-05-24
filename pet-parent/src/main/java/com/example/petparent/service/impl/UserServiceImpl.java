package com.example.petparent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.common.Constants;
import com.example.petparent.controller.dto.UserDTO;
import com.example.petparent.controller.dto.exportDto;
import com.example.petparent.entity.Menu;
import com.example.petparent.entity.User;
import com.example.petparent.exception.ServiceException;
import com.example.petparent.mapper.RoleMapper;
import com.example.petparent.mapper.RoleMenuMapper;
import com.example.petparent.service.IMenuService;
import com.example.petparent.service.IUserService;
import com.example.petparent.mapper.UserMapper;
import com.example.petparent.utils.SeqGenerateUtil;
import com.example.petparent.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    private static final Log LOG = Log.get();

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService iMenuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
//        // 用户密码 md5加密
//        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();

            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);

            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        // 用户密码 md5加密
        //userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            userDTO.setId(SeqGenerateUtil.seqGenerate().toString());
            BeanUtil.copyProperties(userDTO, one, true);
            // 默认一个普通用户的角色
//            one.setRole(RoleEnum.ROLE_STUDENT.toString());
//            if (one.getNickname() == null) {
//                one.setNickname(one.getUsername());
//            }
            userMapper.insert(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }


    @Override
    public User getById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getOne(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage queryAllList(User user) {
        IPage iPage;
        int pageNum = Math.toIntExact(user.getPageNum());
        Page<User> userPage = new Page<>(pageNum, user.getPageSize());
        iPage = userMapper.selectUserPage(userPage, user);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            userPage = new Page<>(pageNum, user.getPageSize());
            iPage = userMapper.selectUserPage(userPage, user);
        }
        return iPage;
    }

    @Override
    public boolean deleteUser(String id) {
        return userMapper.deleteByUserId(id);
    }

    @Override
    public boolean resetPassword(User user) {
        user.setPassword("123456");
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean deleteAllUser(List<User> users) {
        List<String> usersId = new ArrayList<>();
        users.forEach(e->{
            e.setStatus("0");
            userMapper.updateById(e);
        });
        return true;
    }

    @Override
    public boolean addUser(User user) {
        String id = SeqGenerateUtil.seqGenerate().toString();
        if (StringUtils.isEmpty(userMapper.selectById(user.getId()))){
            user.setId(id);
            user.setPassword("123456");
            return userMapper.insert(user) > 0;
        }else {
            return userMapper.updateById(user) > 0;
        }

    }

    @Override
    public boolean modifyPassword(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public List<exportDto> listUsers() {
        return userMapper.listUsers();
    }

    @Override
    public boolean addUsers(List<User> users) {
        users.forEach( p -> {
            String id = SeqGenerateUtil.seqGenerate().toString();
            p.setId(id);
            p.setPassword("123456");
            userMapper.insert(p);
        });
        return true;
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "用户名已存在");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        String roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<String> menuIds = roleMenuMapper.selectByRoleId(roleId);
        // 查出系统所有的菜单(树形)
        List<Menu> menus = iMenuService.getMenuTree();
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        //removeChildren(menuIds,menus,roleMenus);
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            if (!CollectionUtils.isEmpty(children)){
//                children.removeIf(child -> !menuIds.contains(child.getId()));
                removeChildren(menuIds,children,roleMenus);
            }
            if (menuIds.contains(menu.getId()) || !CollectionUtils.isEmpty(menu.getChildren())) {
                roleMenus.add(menu);
            }
        }
        return roleMenus;
    }

    private List<Menu> removeChildren( List<String> menuIds,List<Menu> menus,List<Menu> roleMenus){
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu next = iterator.next();
            if (!menuIds.contains(next.getId())){
                iterator.remove();
            }
            List<Menu> children = next.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            if (!CollectionUtils.isEmpty(children)){
                removeChildren(menuIds,children,roleMenus);
            }
        }
//        // 筛选当前用户角色的菜单
//        for (Menu menu : menus) {
//            if (!menuIds.contains(menu.getId())) {
//                menus.remove(menu);
//            }
//            List<Menu> children = menu.getChildren();
//            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
//            if (!CollectionUtils.isEmpty(children)){
//                removeChildren(menuIds,children,roleMenus);
//            }
//        }
        return roleMenus;
    }


}
