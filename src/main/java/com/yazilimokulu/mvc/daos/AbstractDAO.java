package com.yazilimokulu.mvc.daos;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.yazilimokulu.utils.HibernateUtil;

public abstract class AbstractDAO<T> {

	final static Logger logger = Logger.getLogger(HibernateUtil.class);

	protected Session session;
	protected Transaction tx;
	protected Class<T> type;

	public AbstractDAO() {
	}

	public void saveOrUpdate(T obj) {
		try {
			startOperation();
			session.saveOrUpdate(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public void delete(T obj) {
		try {
			startOperation();
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public T find(Class clazz,Long id) {
		T obj = null;
		try {
			startOperation();
			T value=null;
			obj = (T) session.load(clazz, id);
			tx.commit();
		} catch (HibernateException e) {
			logger.error(e);
		} finally {
			HibernateUtil.close(session);
		}
		return obj;
	}

	@SuppressWarnings("deprecation")
	public List<T> findAll(Class clazz) {
		List<T> objects = null;
		try {
			startOperation();
			objects = (List<T>) session.createQuery("from "+clazz.getSimpleName()).list();
			tx.commit();
			
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
		return objects;
	}

	public void handleException(HibernateException e) {
		HibernateUtil.rollback(tx);
		logger.error(e);
	}

	public void startOperation() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}

}
