package com.koko.spzx.mapper;

import com.koko.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {

    List<SysRole> findAllRoles();

    int deleteRolesByUserId(Long userId);

    int setUserRole(Long userId, Long roleId); //@Param可以省略，只要参数名和sql中的一致

    List<Long> findRolesByUserId(Long userId);
}
