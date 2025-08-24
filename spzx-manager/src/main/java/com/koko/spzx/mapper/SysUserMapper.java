package com.koko.spzx.mapper;

import com.koko.spzx.model.dto.system.SysUserDto;
import com.koko.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser findUserByUsername(String username);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    int insert(SysUser sysUser);

    int update(SysUser sysUser);

    int delete(Long userId);
}
