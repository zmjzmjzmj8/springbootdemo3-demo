package org.zmj.springbootdemo.demo.config.transaction;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.zmj.springbootdemo.demo.config.transaction.jta.platform.AtomikosJtaPlatform;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Created by JACK on 2017/4/20.
 */
@Configuration
public class TransactionManagerConfiguration {
    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager transactionManager() throws SystemException {
        UserTransaction userTransaction = userTransaction();
        TransactionManager atomikosTransactionManager = atomikosTransactionManager();
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

    //本地事务管理
    @Bean(name = "atomikosJtaPlatform")
    public AtomikosJtaPlatform atomikosJtaPlatform(){
        AtomikosJtaPlatform atomikosJtaPlatform = new AtomikosJtaPlatform();
        try {
            AtomikosJtaPlatform.setTransactionManager(atomikosTransactionManager());
            AtomikosJtaPlatform.setUserTransaction(userTransaction());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return atomikosJtaPlatform;
    }
}
