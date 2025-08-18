package com.koko.spzx.util;

import com.koko.spzx.model.entity.system.SysUser;

/* 存取数据的工具类，封装ThreadLocal */
public class AuthContextUtil {

    /* 全局threadLocal对象 */
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    /* 存数据的方法 */
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    /* 取数据的方法 */
    public static void get() {
        SysUser sysUser = threadLocal.get();
    }

    /* 移除数据的方法 */
    public static void remove() {
        threadLocal.remove();
    }
}
