package com.koko.spzx.service;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.dto.system.SysUserDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.system.LoginVo;
import com.koko.spzx.model.vo.system.SysMenuVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

//    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    void insert(SysUser sysUser);

    void update(SysUser sysUser);

    void delete(Long userId);


}
