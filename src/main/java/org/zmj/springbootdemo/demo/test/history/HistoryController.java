package org.zmj.springbootdemo.demo.test.history;


import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zmj.springbootdemo.demo.commmon.CommonController;
import org.zmj.springbootdemo.demo.commmon.JsonAnnotation;
import org.zmj.springbootdemo.demo.commmon.JsonpAnnotation;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;

@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "/apiCall")
public class HistoryController extends CommonController{

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryManager historyManager;

    @RequestMapping(value = "/showHistory" , method = RequestMethod.GET)
    @ResponseBody
    @JsonAnnotation
    public String showHistory(){
        return JSONArray.fromObject(historyManager.findAll()).toString();
    }

    @RequestMapping(value = "/showPageHistory" , method = RequestMethod.POST,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String showHistory(String page , String size ,String[] sortDirections , String[] sortProperties)throws CommonException{
        if(page==null|| "".equals(page) ||size==null|| "".equals(size)){
            throw new CommonException("不能为空");
        }
        System.out.println(page+size);
        Gson gson = new Gson();
        return JSONArray.fromObject(historyManager.findAll(page,size,sortDirections,sortProperties)).toString();
    }
}
