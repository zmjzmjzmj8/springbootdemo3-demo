package org.zmj.springbootdemo.demo.commmon.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmj.springbootdemo.demo.commmon.CommonManager;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysRoleDao;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.func.dao.Sys_role_userDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysRole;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;
import org.zmj.springbootdemo.demo.mapper.func.pojo.Sys_role_user;
import org.zmj.springbootdemo.demo.mapper.test.dao.HistoryDao;
import org.zmj.springbootdemo.demo.utils.MD5Util;


@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterManagerImpl extends CommonManager implements RegisterManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysUserDao userDao;

    @Autowired
    Sys_role_userDao sys_role_userDao;

    @Autowired
    SysRoleDao roleDao;

    @Autowired
    HistoryDao historyDao;
    /**
     * 注册功能
     * @param usermane 用户名
     * @param password 密码
     * @return 用户ID
     * @throws Exception
     */
    @Override
    public Long Register(String usermane, String password) throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(usermane);
        sysUser.setPassword(MD5Util.encode(password));
        logger.info(password);
        logger.info(MD5Util.encode(password));
        SysRole role = roleDao.findByName("ROLE_USER");
        sysUser = userDao.save(sysUser);
        Sys_role_user sys_role_user= new Sys_role_user(sysUser.getId(),role.getId());
        sys_role_userDao.save(sys_role_user);
        return sysUser.getId();
    }

}
