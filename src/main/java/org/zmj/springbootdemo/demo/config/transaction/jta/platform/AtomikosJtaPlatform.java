package org.zmj.springbootdemo.demo.config.transaction.jta.platform;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Created by JACK on 2017/4/25.
 */
public class AtomikosJtaPlatform extends AbstractJtaPlatform {
    private static TransactionManager transactionManager;
    private static UserTransaction userTransaction;

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return userTransaction;
    }

    public static void setTransactionManager(TransactionManager transactionManager) {
        AtomikosJtaPlatform.transactionManager = transactionManager;
    }

    public static void setUserTransaction(UserTransaction userTransaction) {
        AtomikosJtaPlatform.userTransaction = userTransaction;
    }
}
