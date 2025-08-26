package com.koko.spzx.service;

import com.koko.spzx.model.dto.system.AssignRoleDto;

import java.util.HashMap;

public interface SysUserRoleService {
    HashMap<String, Object> findAllRoles(Long userId);

    void setUserRole(AssignRoleDto assignRoleDto);
}
