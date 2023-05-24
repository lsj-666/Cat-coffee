package com.example.petparent.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petparent.entity.Menu;
import com.example.petparent.mapper.MenuMapper;
import com.example.petparent.service.IMenuService;
import com.example.petparent.utils.SeqGenerateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 12259
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private static final Log LOG = Log.get();

    @Resource
    MenuMapper menuMapper;

    @Override
    public boolean addMenu(Menu menu) {
        if (StringUtils.isEmpty(menu.getPid())) {
            menu.setPid("-1");
        }
        if (StringUtils.isEmpty(menuMapper.selectById(menu.getId()))) {
            menu.setId(SeqGenerateUtil.seqGenerate().toString());
            return menuMapper.insert(menu) > 0;
        } else {
            return menuMapper.updateById(menu) > 0;
        }
    }

    @Override
    public IPage queryAllList(Menu menu) {
        IPage iPage;
        int pageNum = Math.toIntExact(menu.getPageNum());
        Page<Menu> menuPage = new Page<>(pageNum, menu.getPageSize());
        menu.setState("1");
        iPage = menuMapper.selectMenuPage(menuPage, menu);
        if (iPage.getRecords().size() == 0) {
            pageNum = 1;
            menuPage = new Page<>(pageNum, menu.getPageSize());
            iPage = menuMapper.selectMenuPage(menuPage, menu);
        }
        return iPage;
    }

    @Override
    public boolean deleteMenu(String id) {
        List<String> list = new ArrayList<>();
        //查询全量数据并转换成树
        LambdaQueryWrapper<Menu> wrapper = new QueryWrapper<Menu>().lambda();
        wrapper.eq(Menu::getState, "1");
        wrapper.orderByAsc(Menu::getId);
        List<Menu> MenuList = menuMapper.selectList(wrapper);
        Menu build = Menu.builder().id("-1").build();
        Menu children = findChildren(build, MenuList);
        List<Menu> csgDirectoryInfos = children.getChildren();
        //深度遍历树，找到需要删除的节点后，将节点下所有目录Id存入list
        list = (depthTraversal(csgDirectoryInfos, id, list));
        return menuMapper.deleteBatchIds(list) > 0;
    }

    @Override
    public boolean deleteAllMenu(List<Menu> menus) {
        List<String> rolesId = new ArrayList<>();
        menus.forEach(e -> {
            rolesId.add(e.getId());
            deleteMenu(e.getId());
        });
        return true;
    }

    /**
     * 深度优先遍历树节点,找到需要删除的父节点
     *
     * @param menus
     * @return
     */
    private List<String> depthTraversal(List<Menu> menus, String id, List<String> list) {
        if (CollectionUtils.isNotEmpty(menus)) {
            int size = menus.size();
            for (int index = 0; index < size; index++) {
                if (menus.get(index).getPid().equals(id)) {
                    list = depthTraversalDelete(menus, list);
                    list.add(id);
                    return list;
                } else {
                    depthTraversal(menus.get(index).getChildren(), id, list);
                }
            }
        }
        return list;
    }

    /**
     * 根据父节点，遍历需要删除的所有子节点,记录到list
     *
     * @param menus
     * @return
     */
    private List<String> depthTraversalDelete(List<Menu> menus, List<String> list) {
        if (CollectionUtils.isNotEmpty(menus)) {
            int size = menus.size();
            for (int index = 0; index < size; index++) {
                list.add(menus.get(index).getId());
                if (CollectionUtils.isNotEmpty(menus.get(index).getChildren())) {
                    depthTraversalDelete(menus.get(index).getChildren(), list);
                }
            }
        }
        return list;
    }

    @Override
    public List<Menu> getMenuTree() {
        LambdaQueryWrapper<Menu> wrapper = new QueryWrapper<Menu>().lambda();
        wrapper.eq(Menu::getState, "1");
        wrapper.orderByAsc(Menu::getSort);
        List<Menu> MenuList = menuMapper.selectList(wrapper);
        Menu build = Menu.builder().id("-1").build();
        Menu children = findChildren(build, MenuList);
        return children.getChildren();
    }

    @Override
    public List<Menu> getPidSelect(String id) {
        LambdaQueryWrapper<Menu> wrapper = new QueryWrapper<Menu>().lambda();
        wrapper.eq(Menu::getState, "1");
        wrapper.notIn(!StringUtils.isEmpty(id), Menu::getId, id);
        wrapper.orderByAsc(Menu::getSort);
        List<Menu> MenuList = menuMapper.selectList(wrapper);
        return MenuList;
    }

    /**
     * 获取目录子节点
     *
     * @param menu
     * @param menuList
     * @return
     */
    private Menu findChildren(Menu menu, List<Menu> menuList) {
        menuList.forEach(e -> {
            if (("-1").equals(e.getPid())) {
                e.setAlwaysShow("true");
            }
            String id = menu.getId();
            String parentId = e.getPid();
            if (id.equals(parentId)) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<Menu>());
                }
                Map<String, Object> meta = new HashMap<>();
                meta.put("name", menu.getName());
                meta.put("pid", menu.getPid());
                meta.put("path", menu.getPath());
                menu.setMeta(meta);
                Menu children = findChildren(e, menuList);
                Map<String, Object> childrenMeta = new HashMap<>();
                childrenMeta.put("name", children.getName());
                childrenMeta.put("pid", children.getPid());
                childrenMeta.put("path", children.getPath());
                menu.setMeta(childrenMeta);
                menu.getChildren().add(children);
            }
        });
        return menu;
    }

}
