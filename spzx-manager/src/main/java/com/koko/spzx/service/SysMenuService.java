package com.koko.spzx.service;

import com.koko.spzx.model.entity.system.SysMenu;
import com.koko.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAllMenuNodes();

    void insert(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void delete(Long menuId);

    List<SysMenuVo> findUserMenuList();

}
