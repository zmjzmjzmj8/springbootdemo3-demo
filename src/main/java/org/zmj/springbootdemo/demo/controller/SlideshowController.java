package org.zmj.springbootdemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zmj.springbootdemo.demo.common.RestfulResult;
import org.zmj.springbootdemo.demo.service.slideshow.SlideshowManager;
import org.zmj.springbootdemo.demo.utils.RestfulResultUtils;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author : zmj
 * @description :轮播图控制器
 * ---------------------------------
 */
@RestController
@RequestMapping(value = "/apiCall")
public class SlideshowController {
    @Autowired
    SlideshowManager slideshowManager;

    /**
     * 显示所有轮播图信息
     * @return
     */
    @RequestMapping(value = "/getAllSlideshow", method = RequestMethod.GET)
    public RestfulResult getAllSlideshow() {
        return RestfulResultUtils.success(slideshowManager.showAliiSlide());
    }
}
