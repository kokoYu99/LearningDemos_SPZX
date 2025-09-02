package com.koko.spzx.service.impl;

import com.koko.spzx.exception.SysMenuException;
import com.koko.spzx.mapper.SysRoleMenuMapper;
import com.koko.spzx.model.dto.system.AssignMenuDto;
import com.koko.spzx.model.entity.system.SysMenu;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysMenuService;
import com.koko.spzx.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper mapper;
    private final SysMenuService sysMenuService;

    public SysRoleMenuServiceImpl(SysRoleMenuMapper mapper, SysMenuService sysMenuService) {
        this.mapper = mapper;
        this.sysMenuService = sysMenuService;
    }

    /* 根据角色id，获取所有菜单(多级)，包括已分配到的菜单(回显) */
    @Override
    public Map<String, Object> findRoleMenuByRoleId(Long roleId) {

        //获取所有菜单(多级)
        List<SysMenu> allMenuNodesList = sysMenuService.findAllMenuNodes();

        //获取已分配到的菜单(数据回显)
        List<Long> roleMenuIds = mapper.findRoleMenuIdsByRoleId(roleId);

        //将以上两个结果封装到map中，返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("allMenuNodesList", allMenuNodesList);
        map.put("roleMenuIds", roleMenuIds);
        return map;
    }

    /*  为角色添加菜单数据 */
    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {
        //1. 根据roleId，先将角色原本的菜单数据清空
        Long roleId = assignMenuDto.getRoleId();
        int del = mapper.deleteRoleMenusByRoleId(roleId);
        System.out.println("del = " + del);

        //2. 获取分配给角色的菜单数据集合
        List<Map<String, Number>> menuInfoList = assignMenuDto.getMenuIdList();
        //一个Map=一个菜单的id、is_half，多个map构成此List

        //3. 判断是否为空，不为空，进行添加
        if (menuInfoList != null && menuInfoList.size() > 0) {
            //4. 将dto对象传给mapper，在mapper中遍历添加菜单数据
            int ins = mapper.doAssign(assignMenuDto);
            if (ins == 0) {
                throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
            }
        }
    }


}
