package org.zmj.springbootdemo.demo.config.datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zmj.springbootdemo.demo.config.datasource.property.OrderDatasourceProperties;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by JACK on 2017/4/20.
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"org.zmj.springbootdemo.demo.mapper.test.dao"},
        entityManagerFactoryRef = "orderEntityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class OrderDataSourceConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean(name = "orderDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dataSource(OrderDatasourceProperties orderDatasourceProperties) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(orderDatasourceProperties.getUrl());
        mysqlXaDataSource.setPassword(orderDatasourceProperties.getPassword());
        mysqlXaDataSource.setUser(orderDatasourceProperties.getUsername());
        mysqlXaDataSource.setAutoReconnect(true);
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("orderDB");
        xaDataSource.setMinPoolSize(5);
        xaDataSource.setMaxPoolSize(20);
        xaDataSource.setTestQuery("Select 1 ");
        xaDataSource.setMaxIdleTime(5);
        return xaDataSource;

    }

    @Bean(name = "orderEntityManagerFactory")
    @DependsOn({"atomikosJtaPlatform"}) //需要先注册atomikosJtaPlatform
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("orderDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJtaDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("org.zmj.springbootdemo.demo.mapper.test.pojo");
        entityManagerFactory.setPersistenceUnitName("orderPersistenceUnit");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = new Properties();
        //jta设置
        //properties.put("hibernate.current_session_context_class", "jta");
        //properties.put("hibernate.transaction.factory_class", "org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory");
        //这里指定我们自己创建的AtomikosJtaPlatform
        properties.put("hibernate.transaction.jta.platform", "org.zmj.springbootdemo.demo.config.transaction.jta.platform.AtomikosJtaPlatform");
        entityManagerFactory.setJpaProperties(properties);

        return entityManagerFactory;
    }
}
