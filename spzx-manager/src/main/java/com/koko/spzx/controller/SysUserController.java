package com.koko.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.koko.spzx.model.dto.system.SysUserDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    private final SysUserService service;

    public SysUserController(SysUserService service) {
        this.service = service;
    }

    /* 查询用户 */
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findByPage(@PathVariable Integer pageNum,
                                                @PathVariable Integer pageSize,
                                                @RequestBody SysUserDto sysUserDto) {

        PageInfo<SysUser> pageInfo = service.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /* 添加用户 */
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser SysUser) {
        service.insert(SysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改用户。sysUser中包含id */
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser SysUser) {
        service.update(SysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除用户(逻辑删除) */
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable Long userId) {
        service.delete(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
