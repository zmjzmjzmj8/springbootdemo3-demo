package org.zmj.springbootdemo.demo.mapper.func.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUserInfo;

public interface SysUserInfoDao extends JpaRepository<SysUserInfo,Long> {
    SysUserInfo findByUsrId(Long id);
}
