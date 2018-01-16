package org.zmj.springbootdemo.demo.service.slideshow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmj.springbootdemo.demo.mapper.func.dao.Bs_slideshowDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.Bs_slideshow;

import java.util.List;

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
 * @description :
 * ---------------------------------
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SlideshowManagerImpl implements SlideshowManager {

    @Autowired
    Bs_slideshowDao bs_slideshowDao;

    @Override
    public List<Bs_slideshow> showAliiSlide() {
        return  bs_slideshowDao.findAll();
    }
}
