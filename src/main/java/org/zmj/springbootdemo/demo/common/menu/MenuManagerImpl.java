package org.zmj.springbootdemo.demo.common.menu;

import org.springframework.stereotype.Service;
import org.zmj.springbootdemo.demo.common.CommonManager;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import javax.persistence.Query;
import java.util.List;

@Service
public class MenuManagerImpl extends CommonManager implements MenuManager{

    @Override
    public List<SysPermission> findAllByUserName(String username) {
        String sql="select p.* " +
                "from sys_permission p " +
                "LEFT JOIN sys_permission_role pr on pr.permissino_id=p.id " +
                "LEFT JOIN sys_role r on pr.role_id=r.id " +
                "LEFT JOIN sys_role_user ru on ru.Role_id=r.id " +
                "LEFT JOIN sys_user u on u.id = ru.User_id " +
                "where u.username=:username";
        Query q = em.createNativeQuery(sql,SysPermission.class);
        q.setParameter("username",username);
        return (List<SysPermission>)q.getResultList();
    }


}
