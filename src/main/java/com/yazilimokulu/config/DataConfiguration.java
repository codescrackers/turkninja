package com.yazilimokulu.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
		dataSource.setUrl("jdbc:mysql://localhost:3306/yazilimokulu?createDatabaseIfNotExist=true");
		Properties props= new Properties();
		props.setProperty("useUnicode", "true");
		props.setProperty("characterEncoding", "utf-8");
		dataSource.setConnectionProperties(props);
		dataSource.setUsername("root");
		dataSource.setPassword("password");
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
		jpaProperties.put("hibernate.hbm2ddl.import_files", "/security-tables.sql,/dummy-data.sql");

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

	
	 @Bean 
	 @Profile("dev")
     public static PropertyPlaceholderConfigurer configurerDevPpc() { 
          PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
          ppc.setLocation(new ClassPathResource("uploading-dev.properties"));
          return ppc; 
     }
	 
	 @Bean 
	 @Profile("prod")
     public static PropertyPlaceholderConfigurer configurerProdPpc() { 
          PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
          ppc.setLocation(new ClassPathResource("uploading-prod.properties"));
          return ppc; 
     } 
	 
	 @Bean 
	 @Profile("test")
     public static PropertyPlaceholderConfigurer configurerTestPpc() { 
          PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
          ppc.setLocation(new ClassPathResource("uploading-test.properties"));
          return ppc; 
     } 
	 
}
