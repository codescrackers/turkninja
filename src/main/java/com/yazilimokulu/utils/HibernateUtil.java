package com.yazilimokulu.utils;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.BookQuestion;
import com.yazilimokulu.mvc.entities.BookQuestionChapter;

public class HibernateUtil {

	final static Logger logger = Logger.getLogger(HibernateUtil.class);

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.addAnnotatedClass(BookQuestionChapter.class);
			configuration.addAnnotatedClass(BookQuestion.class);
			configuration.addAnnotatedClass(Book.class);
			configuration.setProperties(new Properties() {
				{
					put("hibernate.hbm2ddl.auto", "update");
					put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
					put("hibernate.connection.characterEncoding", "utf8");
					put("hibernate.connection.CharSet", "utf8");
					put("hibernate.connection.useUnicode", "true");
				}
			});
			return configuration.buildSessionFactory(
					new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was an error building the factory");
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void close(Session session) {
		if (session != null) {
			try {
				session.close();
			} catch (HibernateException ignored) {
				logger.error("Couldn't close Session", ignored);
			}
		}
	}

	public static void rollback(Transaction tx) {
		try {
			if (tx != null) {
				tx.rollback();
			}
		} catch (HibernateException ignored) {
			logger.error("Couldn't rollback Transaction", ignored);
		}
	}
}
