package com.yazilimokulu.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import com.yazilimokulu.mvc.daos.ExtendedRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.yazilimokulu.mvc.daos" }, repositoryBaseClass = ExtendedRepositoryImpl.class)

@EnableTransactionManagement
public class DataConfiguration {

	@Bean("dataSource")
	public org.springframework.jdbc.datasource.DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/yazilimokulu?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("password");
		System.out.println("***************************************");
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
		jpaProperties.put("hibernate.connection.CharSet", "utf8");
		jpaProperties.put("hibernate.connection.useUnicode", "true");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPackagesToScan("com.yazilimokulu.mvc.entities");
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

}
