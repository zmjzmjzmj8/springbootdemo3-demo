package org.zmj.springbootdemo.demo.commmon;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CommonControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(CommonControllerAspect.class);

    @Pointcut("execution(public * org.zmj.springbootdemo.demo.controller.*Controller.*(..))")
    public void message(){

    }
    @Around("message()")
    public  Object doAround(ProceedingJoinPoint pjp) throws Throwable{
        /*HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("message",commonController.message);*/
        Object[] args = pjp.getArgs();
        logger.info("执行之前");
        Object result = pjp.proceed(args);
        Gson gson = new Gson();
        logger.info("请求结束，controller的返回值是 " + gson.toJson(result));
        return result;
    }
}
