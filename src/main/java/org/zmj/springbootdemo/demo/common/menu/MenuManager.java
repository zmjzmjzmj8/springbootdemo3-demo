package org.zmj.springbootdemo.demo.common.menu;

import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.List;

public interface MenuManager {
    List<SysPermission> findAllByUserName(String username);
}
