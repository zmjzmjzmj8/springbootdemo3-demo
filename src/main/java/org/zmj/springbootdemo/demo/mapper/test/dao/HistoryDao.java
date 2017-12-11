package org.zmj.springbootdemo.demo.mapper.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zmj.springbootdemo.demo.mapper.test.pojo.History;

public interface HistoryDao  extends JpaRepository<History,Long> {
}
