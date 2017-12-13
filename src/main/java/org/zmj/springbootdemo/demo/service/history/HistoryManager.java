package org.zmj.springbootdemo.demo.service.history;

import org.springframework.data.domain.Page;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.test.pojo.History;

import java.util.List;

public interface HistoryManager {
    /**
     * 显示所有历史数据
     * @return
     */
    List <History> findAll();

    /**
     * 显示所有历史数据并且分页
     * @param page
     * @param size
     * @param sortDirections
     * @param sortProperties
     * @return
     * @throws CommonException
     */
    Page<History> findAll(String page, String size ,String[] sortDirections , String[] sortProperties) throws CommonException;
}
