package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.system.SysRoleDto;
import com.koko.spzx.model.entity.system.SysRole;
import com.koko.spzx.model.entity.system.SysUser;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void insert(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(Long roleId);
}
