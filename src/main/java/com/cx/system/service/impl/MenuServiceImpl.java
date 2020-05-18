package com.cx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cx.common.entity.MenuTree;
import com.cx.common.entity.Constant;
import com.cx.common.utils.TreeUtil;
import com.cx.system.entity.Menu;
import com.cx.system.mapper.MenuMapper;
import com.cx.system.service.IMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator·
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    @Override
    public List<Menu> findUserPermissions(String username) {
        if(StringUtils.isNotBlank(username)){
            return this.baseMapper.findUserPermissions(username);
        }

        LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<Menu>().select(Menu::getPerms);
        queryWrapper.isNotNull(Menu::getPerms);
        queryWrapper.ne(Menu::getPerms,"");
        return  this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public MenuTree<Menu> findUserMenus(String username) {
        List<Menu> menus = this.baseMapper.findUserMenus(username);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MenuTree<Menu> findMenus(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(queryWrapper);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);

        return TreeUtil.buildMenuTree(trees);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Menu> findMenuList(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getMenuId).orderByAsc(Menu::getOrderNum);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(Menu menu) {
        menu.setCreateTime(new Date());
        this.setMenu(menu);
        this.baseMapper.insert(menu);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        menu.setModifyTime(new Date());
        this.setMenu(menu);
        this.baseMapper.updateById(menu);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMeuns(String menuIds) {
        String[] menuIdsArray = menuIds.split(StringPool.COMMA);
        this.delete(Arrays.asList(menuIdsArray));
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public MenuTree<Menu> findAllMenus() {
        LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getType, Constant.STATE_0);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(queryWrapper);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }

    /**
     * 根据权限名称获取按钮权限
     *
     * @param roleNames
     * @return
     */
    @Override
    public List<String> findBtnPermissions(String roleNames) {
        if(StringUtils.isNotBlank(roleNames)){
            return  this.baseMapper.findBtnPermissions(Arrays.asList(roleNames.split(StringPool.COMMA)));
        }

        return  this.baseMapper.findBtnPermissions(null);
    }

    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(String.valueOf(menu.getMenuId()));
            tree.setParentId(String.valueOf(menu.getParentId()));
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getUrl());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_NODE);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
    }

    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }
}