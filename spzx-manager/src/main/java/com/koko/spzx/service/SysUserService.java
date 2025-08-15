package com.koko.spzx.service;

import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.vo.system.LoginVo;
import org.springframework.stereotype.Service;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
