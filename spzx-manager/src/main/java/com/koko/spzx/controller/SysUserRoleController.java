package com.koko.spzx.controller;

import com.koko.spzx.model.dto.system.AssignRoleDto;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysUserRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("admin/system/sysUserRole")
public class SysUserRoleController {

    private final SysUserRoleService service;

    public SysUserRoleController(SysUserRoleService service) {
        this.service = service;
    }

    /* 分配角色的弹框中，获取所有角色 */
    @GetMapping("/findAllRoles")
    public Result findAllRoles(){
        HashMap<String, Object> map= service.findAllRoles();
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


    /* 分配角色 */
    @PostMapping("/setUserRole")
    public Result setUserRole(@RequestBody AssignRoleDto assignRoleDto){
        service.setUserRole(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
