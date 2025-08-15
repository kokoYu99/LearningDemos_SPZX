package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    SysUser findUserByUsername(String username);
}
