package com.koko.spzx.service;

import com.koko.spzx.model.dto.system.AssignMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    Map<String, Object> findRoleMenuByRoleId(Long roleId);

    void doAssign(AssignMenuDto assignMenuDto);
}
