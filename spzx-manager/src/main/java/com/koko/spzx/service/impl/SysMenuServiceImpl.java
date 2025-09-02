package com.koko.spzx.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.koko.spzx.exception.SysMenuException;
import com.koko.spzx.mapper.SysMenuMapper;
import com.koko.spzx.mapper.SysRoleMenuMapper;
import com.koko.spzx.model.entity.system.SysMenu;
import com.koko.spzx.model.vo.common.ResultCodeEnum;
import com.koko.spzx.model.vo.system.SysMenuVo;
import com.koko.spzx.service.SysMenuService;
import com.koko.spzx.util.AuthContextUtil;
import com.koko.spzx.utils.MenuHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {


    /* 构造器注入 */
    private final SysMenuMapper menuMapper;
    private final SysRoleMenuMapper roleMenuMapper;

    public SysMenuServiceImpl(SysMenuMapper menuMapper, SysRoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    /* --------------------------- CRUD --------------------------- */

    /* 查询所有菜单 */
    @Override
    public List<SysMenu> findAllMenuNodes() {
        //获取所有菜单项
        List<SysMenu> allMenuNodes = menuMapper.findAllMenuNodes();

        //非空判断，如果非空，再进行处理
        if (allMenuNodes == null) {
            return null;
        }

        //用工具类处理为多级菜单
        List<SysMenu> menuTreeList = MenuHelper.buildTreeList(allMenuNodes);

        //返回
        return menuTreeList;

    }

    @Override
    public void insert(SysMenu sysMenu) {
        //生成主键值，并回显
        int insert = menuMapper.insert(sysMenu);

        //如果存在父菜单，更新父菜单的isHalf值
        updateParentMenuIsHalf(sysMenu);

        if (insert != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    private void updateParentMenuIsHalf(SysMenu sysMenu) {
        //获取父菜单
        SysMenu parentMenu = menuMapper.findMenuById(sysMenu.getParentId());

        if (parentMenu != null) {
            //如果存在父菜单，将父菜单的isHalf设为1
            int update = roleMenuMapper.updateRoleMenuIsHalf(parentMenu.getId());
            //返回值 update 代表受影响的行数
            //为什么不根据update的值进行异常处理？
            //        UPDATE  `sys_role_menu`
            //        SET is_half=1
            //        WHERE menu_id=#{menuId}
            // update 可能是0（没有角色关联该菜单），也可能大于1（多个角色关联该菜单）。
            // 在这个业务场景下，我们不关心具体更新了多少行，只要操作不抛出SQL异常即可。

            //递归调用
            updateParentMenuIsHalf(parentMenu);
        }
    }

    @Override
    public void update(SysMenu sysMenu) {
        int update = menuMapper.update(sysMenu);
        if (update != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public void delete(Long menuId) {
        //先查询是否存在子菜单，如果存在，不允许删除，抛出异常
        if (menuMapper.countChildMenu(menuId) > 0) {
            throw new SysMenuException(ResultCodeEnum.NODE_ERROR);
        }

        //没有子菜单，可以删除
        int delete = menuMapper.delete(menuId);

        if (delete != 1) {
            throw new SysMenuException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


    /* -------------------------------------------------------------- */

    /* 获取用户可以操作的菜单 */
    @Override
    public List<SysMenuVo> findUserMenuList() {

        //1. 从threadLocal获取用户id，查询该用户所有可操作的菜单
        Long userId = AuthContextUtil.get().getId();
        List<SysMenu> menuList = menuMapper.findUserMenuList(userId);

        //2. 将menuList构建成多级菜单(树形集合)
        List<SysMenu> sysMenuList = MenuHelper.buildTreeList(menuList);

        //3. 构建vo集合，将 List<SysMenu> -> List<SysMenuVo>
        List<SysMenuVo> sysMenuVoList = buildMenus(sysMenuList);

        return sysMenuVoList;
    }

    /* 构建菜单vo集合，将 List<SysMenu> -> List<SysMenuVo> */
    private List<SysMenuVo> buildMenus(List<SysMenu> menuList) {
        //1. 创建空的vo集合，存储vo对象
        List<SysMenuVo> sysMenuVoList = new ArrayList<>();

        //2. 遍历菜单集合，将每个菜单项的数据赋值给vo对象
        menuList.forEach(menu -> {
            //创建新的vo对象
            SysMenuVo sysMenuVo = new SysMenuVo();

            //将菜单项的数据，赋给vo对象
            sysMenuVo.setTitle(menu.getTitle());
            sysMenuVo.setName(menu.getComponent());
            List<SysMenu> children = menu.getChildren();
            if (!CollectionUtil.isEmpty(children)) {
                //遍历，返回每一层的vo集合
                sysMenuVo.setChildren(buildMenus(children));
            }

            //将vo对象放入集合
            sysMenuVoList.add(sysMenuVo);
        });

        //3. 返回构建好的vo集合
        return sysMenuVoList;
    }
}
