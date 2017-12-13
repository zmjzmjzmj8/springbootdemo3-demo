package org.zmj.springbootdemo.demo.commmon.Aspect;

import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.utils.RestfulResultUtils;

@Aspect
@Component
public class RestfulControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(RestfulControllerAspect.class);

    @Pointcut("@annotation(RestfulAnnotation)")
    public void restController(){

    }

    @Around("restController()")
    public  Object doAround(ProceedingJoinPoint pjp) throws CommonException{
        Object result;
        String methodName = pjp.getSignature().getName();
        try {
            // 获取目标方法原始的调用参数
            Object[] args = pjp.getArgs();
            // 保存目标方法执行后的返回值
            result = pjp.proceed(args);
            return JSONObject.fromObject(RestfulResultUtils.success(result)).toString();
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new CommonException(throwable.getMessage());
        }
    }

    @AfterReturning(pointcut = "(restController())",returning = "object")
    public void doAfterReturning(Object object){
        logger.info("response={}",object);
    }
}
