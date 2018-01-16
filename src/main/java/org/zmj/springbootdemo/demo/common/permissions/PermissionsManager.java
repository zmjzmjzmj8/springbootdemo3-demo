package org.zmj.springbootdemo.demo.common.permissions;

import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.List;

public interface PermissionsManager {

    /**
     * 查询所有权限
     * @return
     */
    public List<SysPermission> showAllPermissions();
}
