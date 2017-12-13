package org.zmj.springbootdemo.demo.commmon.Aspect;

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
public class JsonpControllerAspect {
    private static final Logger logger  = LoggerFactory.getLogger(JsonpControllerAspect.class);

    public static final String JSONP_CALLBACK = "jsonpCallback";

    @Pointcut("@annotation(JsonpAnnotation)")
    public void jsonpController(){

    }

    @Around("jsonpController()")
    public  Object doAround(ProceedingJoinPoint pjp) throws CommonException{
        String jsonpcallback = JSONP_CALLBACK;
        Object result=null;
        JSONObject jsonObject = new JSONObject();
        String methodName = pjp.getSignature().getName();
        try {
            logger.info("执行"+methodName+"之前");
            // 获取目标方法原始的调用参数
            Object[] args = pjp.getArgs();
            if(args != null && args.length >0)
            {
                // 获得目标方法的第一个参数
                jsonpcallback = (String) args[0];
            }
            // 保存目标方法执行后的返回值
            result = pjp.proceed(args);

            jsonObject.put("status","0");
            jsonObject.put("msg","");
            if(result.toString().startsWith("{")) {
                jsonObject.put("result", JSONObject.fromObject(result));
            } else if(result.toString().startsWith("[")) {
                jsonObject.put("result", JSONArray.fromObject(result));
            } else {
                jsonObject.put("result", result);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            jsonObject = new JSONObject();
            jsonObject.put("status","1");
            jsonObject.put("msg",e.getMessage());
        }finally {
            if(jsonpcallback!=null){
                result = jsonpcallback+"("+jsonObject+")";
            }else {
                result = JSONP_CALLBACK +"("+jsonObject+")";
            }
            logger.info("请求结束，"+methodName+"的返回值是 " +result);
            return result;
        }
    }
}
