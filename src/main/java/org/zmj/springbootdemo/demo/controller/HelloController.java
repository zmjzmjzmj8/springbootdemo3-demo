package org.zmj.springbootdemo.demo.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zmj.springbootdemo.demo.common.CommonController;
import org.zmj.springbootdemo.demo.common.aspect.RestfulAnnotation;
import org.zmj.springbootdemo.demo.common.exception.CommonException;
import org.zmj.springbootdemo.demo.common.register.RegisterManager;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;
import org.zmj.springbootdemo.demo.utils.VerifyCodeUtils;
import org.zmj.springbootdemo.demo.utils.sysenum.ErrorCode;

import java.util.Objects;

@Controller
public class HelloController extends CommonController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysUserDao userDao;

    @Autowired
    private RegisterManager registerManager;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String say() {
        logger.info("Hello");
        logger.warn("Hello");
        logger.error("Hello");
        logger.debug("Hello");
        try {
            message = "success";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "hello";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        logger.info("home");
        return "home";
    }

    @RequestMapping(value = "/home/home2", method = RequestMethod.GET)
    public String home2() {
        logger.info("home2");
        return "home2";
    }

    @RequestMapping(value = "/login_error", method = RequestMethod.GET)
    public String loginError() {
        logger.info("loginError");
        return "loginError";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        logger.info("login");
        return "login";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String username, String password) {
        try {
            if (Objects.equals(username, "") ||
                    Objects.equals(password, "")) {
                return "不能为空";
            }
            SysUser sysUser = userDao.findByUsername(username);
            if (sysUser != null) {
                return "用户已存在";
            }
            registerManager.Register(username, password);
            message = "success";
            return "成功";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "error";
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String history() {
        logger.info("history");
        return "redirect:/home#/home/history";
    }

    /**
     * 生成验证码图片base64方法
     *
     * @throws Exception
     */
    @RequestMapping("/authImageBase64")
    @ResponseBody
    @RestfulAnnotation
    public String authImageBase64() throws Exception {
        JSONObject jsonObject = new JSONObject();
        //利用图片工具生成图片
        //是生成的验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入Session
        session.setAttribute("imageCode", verifyCode);
        //生成图片
        response.setContentType("image/png");
        int w = 100, h = 30;
        jsonObject.put("src", "data:image/jpg;base64," + VerifyCodeUtils.outputImage(w, h, verifyCode));
        return jsonObject.toString();
    }

    /**
     * 生成验证码图片图片方法
     *
     * @throws Exception
     */
    @RequestMapping("/authImage")
    public void authImage() throws Exception {
        //利用图片工具生成图片
        //是生成的验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //将验证码存入Session
        session.setAttribute("imageCode", verifyCode);
        //生成图片
        response.setContentType("image/png");
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/checkImageCode", method = RequestMethod.GET, produces = "application/javascript;charset=UTF-8")
    @ResponseBody
    @RestfulAnnotation
    public String checkImageCode(String imageCode) throws Exception {
        String imageCodeT = (String) session.getAttribute("imageCode");
        if (Objects.equals(imageCodeT, "") || Objects.equals(imageCode, imageCodeT)) {
            throw new CommonException(ErrorCode.VERIFY_ERROR, "验证码错误");
        }
        return "Success";
    }
}
