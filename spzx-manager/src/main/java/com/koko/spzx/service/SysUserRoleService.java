package com.koko.spzx.service;

import com.koko.spzx.model.dto.system.AssignRoleDto;

import java.util.HashMap;

public interface SysUserRoleService {
    HashMap<String, Object> findAllRoles();

    void setUserRole(AssignRoleDto assignRoleDto);
}
