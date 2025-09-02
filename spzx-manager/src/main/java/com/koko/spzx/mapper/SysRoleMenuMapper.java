package com.koko.spzx.mapper;

import com.koko.spzx.model.dto.system.AssignMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    List<Long> findRoleMenuIdsByRoleId(Long roleId);

    int deleteRoleMenusByRoleId(Long roleId);

    int doAssign(AssignMenuDto assignMenuDto);

    int updateRoleMenuIsHalf(Long menuId);
}
