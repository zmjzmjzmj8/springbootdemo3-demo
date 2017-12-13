package org.zmj.springbootdemo.demo.commmon.Aspect;

import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zmj.springbootdemo.demo.commmon.RestfulResult;
import org.zmj.springbootdemo.demo.utils.ZmjUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 所有control的切面，记日志，记录返回
 * @author zmj
 */
@Aspect
@Component
public class ControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut(value = "execution(public * org.zmj.springbootdemo.demo.controller.*.*(..))")
    public void jsonController(){

    }

    /**
     * 记录进入control的参数
     * @param joinPoint
     */
    @Before("jsonController()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}",request.getRequestURL());
        //method
        logger.info("method={}",request.getMethod());
        //ip
        logger.info("ip={}",request.getRemoteAddr());
        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //参数
        logger.info("args={}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 记录response
     * @param object
     */
    @AfterReturning(pointcut = "jsonController()",returning = "object")
    public void doAfterReturning(Object object){
        if(!ZmjUtil.isNullOrEmpty(object)&&object instanceof RestfulResult){
            logger.info("response={}", JSONObject.fromObject(object).toString());
        }else if(!ZmjUtil.isNullOrEmpty(object)&&object instanceof String){
            logger.info("response={}",object.toString());
        }else {
            logger.info("response={}",object);
        }
    }
}
