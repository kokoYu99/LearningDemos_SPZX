package com.koko.spzx.utils;

import com.koko.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/* 构建多级菜单的工具类 */
public class MenuHelper {

    /* 递归的入口 */
    public static List<SysMenu> buildTreeList(List<SysMenu> sysMenuList) {
        //构建空集合，用于存储最终的多级菜单
        ArrayList<SysMenu> menuTreeList = new ArrayList<>();

        //遍历menuList
        //如果 parent_id==0，即第一层菜单，作为入口进行递归
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId() == 0) {
                //将第一层菜单+其子菜单(即children属性)放入最终的菜单列表中
                //子菜单，需要在findChildren()方法中构建
                menuTreeList.add(findChildren(sysMenu, sysMenuList));
            }
        }

        //返回最终的多级菜单集合
        return menuTreeList;
    }

    /* 递归的条件+过程 */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        //1. 构建当前菜单项的子菜单集合
        sysMenu.setChildren(new ArrayList<>());

        //2. 获取当前菜单项的所有子菜单
        //如果集合中有菜单项的parentId与当前菜单项id相等(即子菜单)，就将其放入子菜单集合中
        for (SysMenu treeNode : treeNodes) {
            if (sysMenu.getId().longValue() == treeNode.getParentId().longValue()) {
                //递归，下一层菜单项重复此方法的逻辑，即让子菜单找到自己的所有子菜单，并将自己返回
                sysMenu.getChildren().add(findChildren(treeNode, treeNodes));
            }
        }

        //返回当前菜单项。其中应包括所有子菜单的集合
        return sysMenu;
    }

}
