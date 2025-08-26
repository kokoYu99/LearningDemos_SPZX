package com.koko.spzx.controller;

import com.koko.spzx.model.entity.system.SysMenu;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    private final SysMenuService service;

    public SysMenuController(SysMenuService service) {
        this.service = service;
    }

    /* 查询所有菜单 */
    @GetMapping("/findAllMenuNodes")
    public Result findAllMenuNodes(){
        List<SysMenu> list = service.findAllMenuNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /* 添加菜单 */
    @PostMapping("/saveSysMenu")
    public Result saveSysMenu(@RequestBody SysMenu sysMenu) {
        service.insert(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 修改菜单。sysMenu中包含id */
    @PutMapping("/updateSysMenu")
    public Result updateSysMenu(@RequestBody SysMenu sysMenu) {
        service.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /* 删除菜单(逻辑删除) */
    @DeleteMapping("/deleteSysMenu/{menuId}")
    public Result deleteSysMenu(@PathVariable Long menuId) {
        service.delete(menuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
