package com.koko.spzx.service.impl;

import com.koko.spzx.mapper.SysUserRoleMapper;
import com.koko.spzx.model.dto.system.AssignRoleDto;
import com.koko.spzx.model.entity.system.SysRole;
import com.koko.spzx.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper mapper;

    public SysUserRoleServiceImpl(SysUserRoleMapper mapper) {
        this.mapper = mapper;
    }

    /* 获取所有可分配的角色 */
    @Override
    public HashMap<String, Object> findAllRoles(Long userId) {
        //获取所有角色
        List<SysRole> list = mapper.findAllRoles();

        //获取用户原有的角色，放入list，要在前端回显
        List<Long> sysUserRoles = mapper.findRolesByUserId(userId);

        //封装到Map中，方便在前端用key获取角色数组
        HashMap<String, Object> map = new HashMap();
        map.put("sysRoleList", list);
        map.put("sysUserRoles", sysUserRoles);

        return map;
    }

    /* 根据用户id和角色id，分配角色 */
    @Override
    public void setUserRole(AssignRoleDto assignRoleDto) {
        //从dto对象中，获取用户id和角色id数组
        Long userId = assignRoleDto.getUserId();
        List<Long> roleIdList = assignRoleDto.getRoleIdList();

        //先将该用户原有的角色清空
        int del = mapper.deleteRolesByUserId(userId);
        System.out.println("del = " + del);
        //del==0不代表删除失败，有可能此用户原本就没有任何角色

        //清空后，重新分配角色
        roleIdList.forEach(roleId -> {
            int set = mapper.setUserRole(userId, roleId);
            System.out.println("set = " + set);
        });

    }

}
