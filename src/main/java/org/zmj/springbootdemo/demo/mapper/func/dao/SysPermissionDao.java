package org.zmj.springbootdemo.demo.mapper.func.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.List;

public interface SysPermissionDao extends JpaRepository<SysPermission,Long>,JpaSpecificationExecutor<SysPermissionDao> {

    //List<SysPermission>findAllByUserId(Long id);

    @Override
    List <SysPermission> findAll();
}
