package org.zmj.springbootdemo.demo.utils;

import org.zmj.springbootdemo.demo.commmon.RestfulResult;
import org.zmj.springbootdemo.demo.utils.sysenum.SysCode;

/**
 * RestfulResult对象工厂工具
 * @author zmj
 */
public class RestfulResultUtils {

    /**
     * 返回成功
     * @param object
     * @return
     */
    public static RestfulResult success(Object object){
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setStatus(SysCode.SYS_CODE_STATUS_SUCCESS.getCode());
        restfulResult.setMsg(SysCode.SYS_CODE_STATUS_SUCCESS.getDescription());
        restfulResult.setResult(object);
        return restfulResult;
    }

    /**
     * 返回成功，并且无返回实体
     * @return
     */
    public static RestfulResult success(){
        return success(null);
    }

    /**
     * 返回失败
     * @param code
     * @param msg
     * @return
     */
    public static RestfulResult error(Integer code,String msg){
        RestfulResult restfulResult = new RestfulResult();
        restfulResult.setStatus(code);
        restfulResult.setMsg(msg);
        restfulResult.setResult(null);
        return restfulResult;
    }
}
