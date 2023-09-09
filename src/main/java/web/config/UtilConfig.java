package web.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class UtilConfig {


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("web.model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sqltable");
        dataSource.setUsername( "root" );
        dataSource.setPassword( "Rooot321-+" );
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }
/*@Configuration
@EnableTransactionManagement
public class UtilConfig {

    private final Environment environment;

    public UtilConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean () {
        LocalContainerEntityManagerFactoryBean loc  = new LocalContainerEntityManagerFactoryBean();
        loc.setDataSource(dataSource());
        loc.setPackagesToScan(environment.getRequiredProperty("spring-mvc.model"));
        loc.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        loc.setJpaProperties(getHibernteProperties());

        return loc;

    }

    private Properties getHibernteProperties() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public DataSource dataSource (){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(environment.getRequiredProperty("jdbc:mysql://localhost:3306/sqltable"));
        basicDataSource.setDriverClassName(environment.getRequiredProperty("com.mysql.cj.jdbc.Driver"));
        basicDataSource.setUsername(environment.getRequiredProperty("root"));
        basicDataSource.setPassword(environment.getRequiredProperty("Rooot321-+"));
        return basicDataSource;
    }*/
}


