package com.yazilimokulu.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yazilimokulu.mvc.daos.ExtendedRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = {"com.yazilimokulu.mvc.daos"},
	repositoryBaseClass=ExtendedRepositoryImpl.class
	)

@EnableTransactionManagement
public class DataConfiguration {

	
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.connection.username", "root");
		jpaProperties.put("hibernate.connection.password", "password");
		jpaProperties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/yazilimokulu?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=utf-8");
		jpaProperties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
		jpaProperties.put("hibernate.connection.CharSet", "utf8");
		jpaProperties.put("hibernate.connection.useUnicode", "true");
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPackagesToScan("com.yazilimokulu.mvc.entities");
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	
}
