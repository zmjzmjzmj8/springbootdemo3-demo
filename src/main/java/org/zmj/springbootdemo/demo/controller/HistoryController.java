package org.zmj.springbootdemo.demo.controller;


import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zmj.springbootdemo.demo.commmon.Aspect.RestfulAnnotation;
import org.zmj.springbootdemo.demo.commmon.CommonController;
import org.zmj.springbootdemo.demo.commmon.RestfulResult;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.service.history.HistoryManager;
import org.zmj.springbootdemo.demo.utils.ErrorCode;
import org.zmj.springbootdemo.demo.utils.RestfulResultUtils;

@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/apiCall")
public class HistoryController extends CommonController{

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryManager historyManager;

    @RequestMapping(value = "/showHistory" , method = RequestMethod.GET)
    @ResponseBody
    @RestfulAnnotation
    public String showHistory(){
        return JSONArray.fromObject(historyManager.findAll()).toString();
    }

    @RequestMapping(value = "/showPageHistory" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public RestfulResult showHistory(String page , String size , String[] sortDirections , String[] sortProperties)throws CommonException{
        if(page==null|| "".equals(page) ||size==null|| "".equals(size)){
            throw new CommonException(ErrorCode.NULL_ERROR,"不能为空");
        }
        System.out.println(page+size);
        return RestfulResultUtils.success(JSONArray.fromObject(historyManager.findAll(page,size,sortDirections,sortProperties)).toString());
    }
}
