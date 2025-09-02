package com.koko.spzx.controller;

import com.koko.spzx.model.dto.system.AssignMenuDto;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysRoleMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    private final SysRoleMenuService service;

    public SysRoleMenuController(SysRoleMenuService service) {
        this.service = service;
    }

    /* 根据角色id，获取所有菜单，包括已分配到的菜单(回显) */
    @GetMapping("/findRoleMenuByRoleId/{roleId}")
    public Result findRoleMenuByRoleId(@PathVariable Long roleId) {
        Map<String, Object> map = service.findRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    /* 为角色添加菜单数据 */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuDto assignMenuDto) {
        service.doAssign(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
