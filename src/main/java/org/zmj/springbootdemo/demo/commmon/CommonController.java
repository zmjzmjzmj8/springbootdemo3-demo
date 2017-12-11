package org.zmj.springbootdemo.demo.commmon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;
import org.zmj.springbootdemo.demo.utils.ZmjUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommonController {

    @PersistenceContext
    protected EntityManager em;//原生SQL构造器

    protected String message="";

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected ObjectMapper mapper = new ObjectMapper();//jackson 对象转换器

    @Autowired
    SysUserDao sysUserDao;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){

        this.request = request;

        this.response = response;

        this.session = request.getSession();

        this.message="";

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 获取当前用户对象
     * @return
     * @throws Exception
     */
    public SysUser getThisUser() throws Exception{
        String username="";
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            username=userDetails.getUsername();
        }catch (Exception e){
            System.out.println("No session dev...");
            String username2 = request.getHeader("username");
            String password = request.getHeader("password");
            SysUser user = sysUserDao.findByUsername(username2);
            if(password.equals(user.getPassword())){
                username=username2;
            }
        }
        if(!ZmjUtil.isNullOrEmpty(username)){
            SysUser sysUser= sysUserDao.findByUsername(username);
            return sysUser;
        }else {
            throw new CommonException("无法获取当前用户对象，请检查！");
        }
    }

}
