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

/**
 * @author zmj
 */
@Aspect
@Component
public class ControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut(value = "execution(public * org.zmj.springbootdemo.demo.controller.*.*(..))")
    public void jsonController(){

    }

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
        logger.info("args={}",joinPoint.getArgs());
    }

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
    /*@Around("jsonController()")
    public  Object doAround(ProceedingJoinPoint pjp) throws CommonException{
        Object result=null;
        JSONObject jsonObject = new JSONObject();
        String methodName = pjp.getSignature().getName();
        try {
            logger.info("执行"+methodName+"之前");
            // 获取目标方法原始的调用参数
            Object[] args = pjp.getArgs();
            // 保存目标方法执行后的返回值
            result = pjp.proceed(args);
            //成功状态码
            return
        }catch (CommonException e){
            logger.error(e.getMessage());
            jsonObject = new JSONObject();
            //业务报错返回码（已知报错）
            jsonObject.put("status", SysCode.SYS_CODE_STATUS_KNOWS_ERROR.getCode());
            jsonObject.put("msg",SysCode.SYS_CODE_STATUS_KNOWS_ERROR.getInfo()+","+e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            jsonObject = new JSONObject();
            //非业务报错返回码（未知报错）
            jsonObject.put("status",SysCode.SAYS_CODE_STATUS_UNKNOWNS_ERROR.getCode());
            jsonObject.put("msg",SysCode.SAYS_CODE_STATUS_UNKNOWNS_ERROR.getCode()+","+e.getMessage());
        }finally {
            result=jsonObject.toString();
            logger.info("请求结束，"+methodName+"的返回值是 " +result);
            return result;
        }
    }*/
}
