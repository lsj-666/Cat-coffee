package com.example.petparent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.RoleMenu;
import com.example.petparent.mapper.RoleMenuMapper;
import com.example.petparent.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void insertRoleMenu(String roleId, List<String> menuIds) {
        if (!StringUtils.isEmpty(roleId) && !CollectionUtils.isEmpty(menuIds)){
            //先删除当前角色id所绑定的关系
            roleMenuMapper.deleteByRoleId(roleId);

            //再把前端传来的菜单id绑定到当前这个角色的id上去
            for (String menuId: menuIds){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuMapper.insert(roleMenu);
            }
        }


    }

    @Override
    public List<String> getRoleMenu(String roleId, List<String> menuIds) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
