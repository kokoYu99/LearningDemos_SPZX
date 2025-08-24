package com.koko.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.spzx.exception.SysRoleException;
import com.koko.spzx.mapper.SysRoleMapper;
import com.koko.spzx.model.dto.system.SysRoleDto;
import com.koko.spzx.model.entity.system.SysRole;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper mapper;

    public SysRoleServiceImpl(SysRoleMapper mapper) {
        this.mapper = mapper;
    }

    /* 查询角色 */
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {

        //设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        //根据dto中的角色名称，查询角色
        List<SysRole> sysRoleList = mapper.findByPage(sysRoleDto);

        //返回分页后的数据
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);

        return pageInfo;
    }

    @Override
    public void insert(SysRole sysRole) {
        int insert = mapper.insert(sysRole);
        if (insert != 1) {
            throw new SysRoleException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void update(SysRole sysRole) {
        int update = mapper.update(sysRole);
        if (update != 1) {
            throw new SysRoleException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void delete(Long roleId) {
        int delete = mapper.delete(roleId);
        if (delete != 1) {
            throw new SysRoleException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
