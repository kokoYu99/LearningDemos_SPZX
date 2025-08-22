package com.koko.spzx.mapper;

import com.koko.spzx.model.dto.system.SysRoleDto;
import com.koko.spzx.model.entity.system.SysRole;
import com.koko.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    int insert(SysRole sysRole);

    int update(SysRole sysRole);

    int delete(Long roleId);
}
