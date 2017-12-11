package org.zmj.springbootdemo.demo.mapper.func.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;

public interface SysUserDao extends JpaRepository<SysUser,Long>  {

    SysUser findByUsername(String Username);
}
