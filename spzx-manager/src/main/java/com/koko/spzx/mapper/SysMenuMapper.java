package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAllMenuNodes();

    int insert(SysMenu sysMenu);

    int update(SysMenu sysMenu);

    int delete(Long menuId);

    Integer countChildMenu(Long menuId);
}
