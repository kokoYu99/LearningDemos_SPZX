package com.koko.spzx.controller;

import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.Result;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.system.LoginVo;
import com.koko.spzx.model.vo.system.ValidateCodeVo;
import com.koko.spzx.service.SysUserService;
import com.koko.spzx.service.ValidateCodeService;
import com.koko.spzx.util.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;


/*
 * 三种实体类对象
 * dto(data transfer object) 前端传入的对象，封装了请求参数
 * vo(view object) 后端返回的对象，封装了响应结果
 * entity(/domain/pojo) 和数据库对应的对象
 *
 * */

//@CrossOrigin(originPatterns = {"http://localhost:*"}, allowCredentials = "true") //见WebMvcConfig配置类
@RestController
@RequestMapping("admin/system/index")
public class IndexController {
    private final SysUserService sysUserService;
    private final ValidateCodeService validateCodeService;

    /* 使用构造器注入 */
    public IndexController(SysUserService sysUserService, ValidateCodeService validateCodeService) {
        this.sysUserService = sysUserService;
        this.validateCodeService = validateCodeService;
    }

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
        System.out.println("loginDto.getUsername() = " + loginDto.getUsername());
        System.out.println("loginDto.getPassword() = " + loginDto.getPassword());

        //2. 调用service的方法，校验用户名和密码
        LoginVo loginVo = sysUserService.login(loginDto);

        //3. 返回登录结果。结果中封装了token
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    /* 获取验证码接口 */
    @GetMapping("/generateValidateCode")
    @Operation(description = "获取验证码")
    public Result generateValidateCode() {
        ValidateCodeVo vo = validateCodeService.generateValiCode();
        return Result.build(vo, ResultCodeEnum.SUCCESS);
    }

    /* 登录后，获取用户信息接口
    * 优化版：直接从threadLocal中获取SysUser，无需再次查询redis
    * */
    @GetMapping("/userinfo")
    public Result getUserInfo() {
        SysUser sysUser = AuthContextUtil.get();
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }
//    /* 登录后，获取用户信息接口 */
//    @GetMapping("/userinfo")
//    public Result getUserInfo(@RequestHeader(name = "token") String token) {
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
//    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
