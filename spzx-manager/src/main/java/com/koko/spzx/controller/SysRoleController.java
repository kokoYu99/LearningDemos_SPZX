package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.system.SysRoleDto;
import com.koko.spzx.model.entity.system.SysRole;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    private final SysRoleService service;

    public SysRoleController(SysRoleService service) {
        this.service = service;
    }

    /* 查询角色 */
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@PathVariable Integer pageNum,
                                                @PathVariable Integer pageSize,
                                                @RequestBody SysRoleDto sysRoleDto) {

        PageInfo<SysRole> pageInfo = service.findByPage(sysRoleDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /* 添加角色 */
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        service.insert(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改角色。sysUser中包含id */
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        service.update(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除角色(逻辑删除) */
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable Long roleId) {
        service.delete(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
