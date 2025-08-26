package com.koko.spzx.service.impl;

import com.koko.spzx.exception.SysMenuException;
import com.koko.spzx.mapper.SysMenuMapper;
import com.koko.spzx.model.entity.system.SysMenu;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysMenuService;
import com.koko.spzx.utils.MenuHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {


    private final SysMenuMapper mapper;

    public SysMenuServiceImpl(SysMenuMapper mapper) {
        this.mapper = mapper;
    }


    /* 查询所有菜单 */
    @Override
    public List<SysMenu> findAllMenuNodes() {
        //获取所有菜单项
        List<SysMenu> allMenuNodes = mapper.findAllMenuNodes();

        //非空判断，如果非空，再进行处理
        if (allMenuNodes == null) {
            return null;
        }

        //用工具类处理为多级菜单
        List<SysMenu> menuTreeList = MenuHelper.buildTreeList(allMenuNodes);

        //返回
        return menuTreeList;

    }

    @Override
    public void insert(SysMenu sysMenu) {
        //生成主键值，并回显
        int insert = mapper.insert(sysMenu);
        if (insert != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void update(SysMenu sysMenu) {
        int update = mapper.update(sysMenu);
        if (update != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void delete(Long menuId) {
        //先查询是否存在子菜单，如果存在，不允许删除，抛出异常
        if (mapper.countChildMenu(menuId) > 0) {
            throw new SysMenuException(ResultCodeEnum.NODE_ERROR);
        }

        //没有子菜单，可以删除
        int delete = mapper.delete(menuId);

        if (delete != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
