package org.zmj.springbootdemo.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysPermissionDao;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysRoleDao;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ALL", "AlibabaServiceOrDaoClassShouldEndWithImpl"})
@Service
public class CustomUserServiceImpl implements UserDetailsService {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;

    @Autowired
    SysUserDao userDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysPermissionDao sysPermissionDao;

    @Autowired
    MyInvocationSecurityMetadataSourceService myInvocationSecurityMetadataSourceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username:"+username);
        myInvocationSecurityMetadataSourceService.loadResourceDefine();
        //获取用户对象
        SysUser user = userDao.findByUsername(username);
        if (user != null) {
            //获取该用户权限列表
            List<SysPermission> permissions =findAllByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            //遍历权限列表
            for (SysPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User: " + username + " do not exist!");
        }
    }

    private List<SysPermission> findAllByUserId(Long id){
        String sql="select p.* " +
                "from sys_permission p " +
                "LEFT JOIN sys_permission_role pr on pr.permissino_id=p.id " +
                "LEFT JOIN sys_role r on pr.role_id=r.id " +
                "LEFT JOIN sys_role_user ru on ru.Role_id=r.id " +
                "LEFT JOIN sys_user u on u.id = ru.User_id " +
                "where u.id=:id";
        Query q = em.createNativeQuery(sql,SysPermission.class);
        q.setParameter("id",id);
        return (List<SysPermission>)q.getResultList();
    }
}
