package org.zmj.springbootdemo.demo.common.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zmj.springbootdemo.demo.common.CommonManager;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysPermissionDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.List;

@Service
public class PermissionsManagerImpl extends CommonManager implements PermissionsManager {

    @Autowired
    SysPermissionDao permissionDao;

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<SysPermission> showAllPermissions(){
        return permissionDao.findAll();
    }
}
