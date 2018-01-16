package org.zmj.springbootdemo.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmj.springbootdemo.demo.common.register.RegisterManager;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.test.dao.HistoryDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@EnableAutoConfiguration
public class DemoApplicationTests {

	@Autowired
	RegisterManager registerManager;

	@Autowired
	SysUserDao sysUserDao;

	@Autowired
	HistoryDao historyDao;

	@Test
	@Transactional
	@Rollback
	public void TransactionalRollback() throws Exception {
		/*Long re  = registerManager.register("test","test");
		System.out.println(re);

		history history = new history();
		history.setQqid("test");
		history.setHnr("1");
		history.setJitter("1");
		history.setShimmer("1");
		history.setTime(Timestamp.valueOf("2020-2-2 12:12:12"));
		historyDao.save(history);*/

		/*SysUser sysUser =  new SysUser();
		sysUser.setUsername("test");
		sysUser.setPassword("test");
		sysUserDao.save(sysUser);*/
		//int x = 1/0;
	}

	@Test
	@Transactional
	@Rollback(false)
	public void TransactionalNotRollback() throws Exception {
		/*Long re  = registerManager.register("test","test");
		System.out.println(re);
		history history = new history();
		history.setQqid("test");
		history.setHnr("1");
		history.setJitter("1");
		history.setShimmer("1");
		history.setTime(Timestamp.valueOf("2020-2-2 12:12:12"));
		historyDao.save(history);*/
		//int x = 2/0;
	}

}
