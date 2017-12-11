package org.zmj.springbootdemo.demo.commmon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;

@Aspect
@Component
public class JsonControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(JsonControllerAspect.class);

    @Pointcut("@annotation(org.zmj.springbootdemo.demo.commmon.JsonAnnotation)")
    public void jsonController(){

    }

    @Around("jsonController()")
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
            jsonObject.put("status","200");
            jsonObject.put("msg","");
            if(result.toString().startsWith("{")) {
                jsonObject.put("result", JSONObject.fromObject(result));
            } else if(result.toString().startsWith("[")) {
                jsonObject.put("result", JSONArray.fromObject(result));
            } else {
                jsonObject.put("result", result);
            }
        }catch (CommonException e){
            logger.error(e.getMessage());
            jsonObject = new JSONObject();
            //业务报错返回码（已知报错）
            jsonObject.put("status","500");
            jsonObject.put("msg",e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            jsonObject = new JSONObject();
            //非业务报错返回码（未知报错）
            jsonObject.put("status","501");
            jsonObject.put("msg",e.getMessage());
        }finally {
            result=jsonObject.toString();
            logger.info("请求结束，"+methodName+"的返回值是 " +result);
            return result;
        }
    }
}
