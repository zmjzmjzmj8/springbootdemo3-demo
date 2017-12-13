package org.zmj.springbootdemo.demo.commmon.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zmj.springbootdemo.demo.commmon.RestfulResult;
import org.zmj.springbootdemo.demo.utils.RestfulResultUtils;
import org.zmj.springbootdemo.demo.utils.sysenum.SysCode;
import org.zmj.springbootdemo.demo.utils.ZmjUtil;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionTranslator {
    public static final String DEFAULT_ERROR_VIEW = "exception_error";
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public RestfulResult handle(CommonException e){
        if(ZmjUtil.isNullOrEmpty(e.getCode())){
            return RestfulResultUtils.error(SysCode.SAYS_CODE_STATUS_UNKNOWNS_ERROR.getCode(),e.getMessage().trim());
        }
        else{
            return RestfulResultUtils.error(e.getCode(),e.getMessage().trim());
        }
    }
}
