package com.example.petparent.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Role;
import com.example.petparent.mapper.RoleMapper;
import com.example.petparent.service.IRoleService;
import com.example.petparent.utils.SeqGenerateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 12259
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private static final Log LOG = Log.get();

    @Resource
    RoleMapper roleMapper;

    @Override
    public boolean addRole(Role role) {
        if (StringUtils.isEmpty(roleMapper.selectById(role.getId()))){
            role.setId(SeqGenerateUtil.seqGenerate().toString());
            return roleMapper.insert(role) > 0;
        }else {
            return roleMapper.updateById(role) > 0;
        }
    }

    @Override
    public IPage queryAllList(Role role) {
        IPage iPage;
        int pageNum = Math.toIntExact(role.getPageNum());
        Page<Role> rolePage = new Page<>(pageNum, role.getPageSize());
        iPage = roleMapper.selectRolePage(rolePage, role);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            rolePage = new Page<>(pageNum, role.getPageSize());
            iPage = roleMapper.selectRolePage(rolePage, role);
        }
        return iPage;
    }

    @Override
    public boolean deleteRole(String id) {
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteAllRole(List<Role> roles) {
        List<String> rolesId = new ArrayList<>();
        roles.forEach(e->{
            rolesId.add(e.getId());
        });
        return roleMapper.deleteBatchIds(rolesId) > 0;
    }

    @Override
    public List<Role> getSelectRole() {
        LambdaQueryWrapper<Role> wrapper = new QueryWrapper<Role>().lambda();
        wrapper.orderByAsc(Role::getId);
        List<Role> roleList = roleMapper.selectList(wrapper);
        return roleList;
    }

}
