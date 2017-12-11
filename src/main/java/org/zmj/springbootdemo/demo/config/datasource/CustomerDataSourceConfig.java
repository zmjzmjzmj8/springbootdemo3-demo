package org.zmj.springbootdemo.demo.config.datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zmj.springbootdemo.demo.config.datasource.property.CustomerDatasourceProperties;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Created by JACK on 2017/4/20.
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"org.zmj.springbootdemo.demo.mapper.func.dao"},
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class CustomerDataSourceConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Primary
    @Bean(name = "customerDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dataSource(CustomerDatasourceProperties customerDatasourceProperties) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(customerDatasourceProperties.getUrl());
        mysqlXaDataSource.setPassword(customerDatasourceProperties.getPassword());
        mysqlXaDataSource.setUser(customerDatasourceProperties.getUsername());
        mysqlXaDataSource.setAutoReconnect(true);
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("customerDB");
        xaDataSource.setMinPoolSize(5);
        xaDataSource.setMaxPoolSize(20);
        xaDataSource.setTestQuery("Select 1 ");
        xaDataSource.setMaxIdleTime(5);
        return xaDataSource;

    }

    @Primary
    @Bean(name = "customerEntityManagerFactory")
    @DependsOn({"atomikosJtaPlatform"}) //需要先注册atomikosJtaPlatform
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("customerDataSource") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJtaDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("org.zmj.springbootdemo.demo.mapper.func.pojo");
        entityManagerFactory.setPersistenceUnitName("customerPersistenceUnit");
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
