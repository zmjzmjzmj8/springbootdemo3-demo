package org.zmj.springbootdemo.demo.mapper.func.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole,Long> {
    SysRole findByName(String name);
}
