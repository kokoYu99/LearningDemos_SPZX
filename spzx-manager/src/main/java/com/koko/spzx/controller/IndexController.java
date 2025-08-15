package com.koko.spzx.controller;

import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.system.LoginVo;
import com.koko.spzx.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
* 三种实体类对象
* dto(data transfer object) 前端传入的对象，封装了请求参数
* vo(view object) 后端返回的对象，封装了响应结果
* entity(/domain/pojo) 和数据库对应的对象
*
* */

@RestController
@RequestMapping("admin/system/index")
@Tag(name = "IndexController", description = "用户接口")
public class IndexController {

    @Autowired
    private SysUserService service;

    /* 用户登录 */
    @PostMapping("/login")
    @Operation(description = "用户登录校验")
    public Result login(@RequestBody LoginDto loginDto) {
        /*
        * 补充：
        * 前端传来json(application/json) -> @RequestBody处理；
        * 前端传来表单(x-www-form-urlencoded) -> @ModelAttribute处理
        */

        //1. @RequestBody 从方法参数获取前端传入的用户对象

        //2. 调用service的方法，校验用户名和密码
        LoginVo loginVo = service.login(loginDto);

        //3. 返回登录结果。结果中封装了token
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

}
