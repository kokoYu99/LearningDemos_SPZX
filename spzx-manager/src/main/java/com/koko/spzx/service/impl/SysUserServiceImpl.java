package com.koko.spzx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.LoginException;
import com.koko.spzx.exception.SysUserException;
import com.koko.spzx.mapper.SysUserMapper;
import com.koko.spzx.model.dto.system.LoginDto;
import com.koko.spzx.model.dto.system.SysUserDto;
import com.koko.spzx.model.dto.system.SysUserDto;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.entity.system.SysUser;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.system.LoginVo;
import com.koko.spzx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;

    public SysUserServiceImpl(SysUserMapper mapper, RedisTemplate<String, String> redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }

    /* ---------------用户登录/退出登录------------------ */

    /* 登录 */
    @Override
    public LoginVo login(LoginDto loginDto) {

        //先校验验证码，校验通过，再校验用户名和密码
        isCaptchaValid(loginDto);

        //1. 从传入的dto中取出username，去数据库查询user对象
        SysUser dbSysUser = mapper.findUserByUsername(loginDto.getUsername());

        //2. 判断查询到的user对象是否为null

        //2.1 如果user对象为null，或者密码错误，抛异常"用户名或密码错误"
        if (dbSysUser == null || !isPasswordValid(loginDto.getPassword(), dbSysUser.getPassword())) {
            //TODO 后续改为业务异常，由异常处理类统一处理
            throw new LoginException(ResultCodeEnum.LOGIN_ERROR);
        }

        //2.2 密码正确，生成token，存入redis设置timeout，存入vo对象并返回
        return buildLoginVo(dbSysUser);


    }

    /* 登录后，获取用户信息
     * 优化：IndexController的getUserInfo()可以直接从ThreadLocal中获取用户信息，无需再调此方法
     *  */
/*    @Override
    public SysUser getUserInfo(String token) {

        //根据token，从redis获取用户信息
        String userInfo = redisTemplate.opsForValue().get("user:login:" + token);

        //将用户信息的json转换为对象。使用fastjson工具
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);

        //对象转换成功，再返回给前端
        if (sysUser != null) {
            //要将密码置空
            sysUser.setPassword("");
            return sysUser;
        }

        throw new LoginException(ResultCodeEnum.DATA_ERROR);

    }*/

    /* 退出登录 */
    @Override
    public void logout(String token) {
        //删除redis中的用户信息
        Boolean delete = redisTemplate.delete("user:login:" + token);

        //如果删除失败，抛异常
        if (Boolean.FALSE.equals(delete)) {
            throw new LoginException(ResultCodeEnum.LOGOUT_ERROR);
        }
    }

    /* 校验验证码 */
    private void isCaptchaValid(LoginDto dto) {
        //1. 获取用户输入的验证码
        String input_captcha = dto.getCaptcha();

        //2. 获取验证码在redis中对应的key
        String redis_key = dto.getCodeKey();

        //3. 从redis中获取验证码
        String redis_captcha = redisTemplate.opsForValue().get(redis_key);

        //比较验证码。如果redis中无此验证码，或与输入的不匹配，抛异常
        if (redis_captcha == null || !input_captcha.equalsIgnoreCase(redis_captcha)) {
            //验证码错误异常
            throw new LoginException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //如果验证码匹配，将其从redis中删除，节省空间
        redisTemplate.delete(redis_key);

    }

    /* 校验密码 */
    private boolean isPasswordValid(String inputPassword, String dbPassword) {
        //将输入的密码进行md5转换
        String encryptedInput = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        //返回校验结果
        return encryptedInput.equals(dbPassword);
    }

    /* 构建结果对象vo */
    private LoginVo buildLoginVo(SysUser dbSysUser) {
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");

        //存入redis
        //key: "user:login:token"，value: 用户信息
        //过期时间 30min
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(dbSysUser), 30, TimeUnit.MINUTES);

        //创建vo对象，存入token
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        //返回vo对象
        return loginVo;
    }

    /*-------------------------用户CRUD-------------------------*/
    /* 查询用户 */
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto SysUserDto, Integer pageNum, Integer pageSize) {

        //设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        //根据dto中的角色名称，查询角色
        List<SysUser> SysUserList = mapper.findByPage(SysUserDto);

        //返回分页后的数据
        PageInfo<SysUser> pageInfo = new PageInfo<>(SysUserList);

        return pageInfo;
    }

    /* 新增用户 */
    @Override
    public void insert(SysUser sysUser) {
        //1. 判断username是否已占用
        SysUser dbSysUser = mapper.findUserByUsername(sysUser.getUsername());

        //2.1 如果用户存在，说明已占用，抛异常
        if (dbSysUser != null) {
            throw new SysUserException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //2.2 如果未占用，对密码加密MD5(Spring的DigestUtils工具)
        String md5Password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5Password); //存入加密后的密码
        sysUser.setStatus(1); //1正常 0停用

        //3. 将用户存入数据库
        int insert = mapper.insert(sysUser);
        if (insert != 1) {
            throw new SysUserException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /* 修改用户 */
    @Override
    public void update(SysUser sysUser) {
        int update = mapper.update(sysUser);
        if (update != 1) {
            throw new SysUserException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void delete(Long UserId) {
        int delete = mapper.delete(UserId);
        if (delete != 1) {
            throw new SysUserException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

}
