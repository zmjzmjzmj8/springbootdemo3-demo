package org.zmj.springbootdemo.demo.test.history;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.test.dao.HistoryDao;
import org.zmj.springbootdemo.demo.mapper.test.pojo.History;
import org.zmj.springbootdemo.demo.utils.ZmjUtil;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class HistoryManagerImpl implements HistoryManager{

    @Autowired
    HistoryDao historyDao;

    @Override
    public List<History> findAll() {
        return historyDao.findAll();
    }

    @Override
    public Page<History> findAll(String page, String size ,String[] sortDirections , String[] sortProperties) throws CommonException{
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        Sort sort = null;
        if(ZmjUtil.isNullOrEmpty(sortDirections)||ZmjUtil.isNullOrEmpty(sortProperties)){
            sort=new Sort(Sort.Direction.ASC,"id");
        }
        else{
            if(sortDirections.length!=sortProperties.length){
                throw new CommonException("入参排序数组长度不一致");
            }
            for(int i=0;i<sortDirections.length;i++){
                if(ZmjUtil.isNullOrEmpty(sortDirections[i])||ZmjUtil.isNullOrEmpty(sortProperties[i])) {
                    continue;
                }
                if("ASC".equals(sortDirections[i])){
                    orders.add( new Sort.Order(Sort.Direction.ASC, sortProperties[i]));
                }else if("DESC".equals(sortDirections[i])){
                    orders.add( new Sort.Order(Sort.Direction.DESC, sortProperties[i]));
                }
            }
            if (ZmjUtil.isNullOrEmpty(orders)) {
                orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
            }
            sort = new Sort(orders);
        }
        Pageable pageable = new PageRequest(Integer.valueOf(page),Integer.valueOf(size),sort);
        Page<History> historyPage = historyDao.findAll(pageable);
        System.out.println("总条数："+historyPage.getTotalElements());
        System.out.println("总页数："+historyPage.getTotalPages());
        return historyPage;
    }
}
