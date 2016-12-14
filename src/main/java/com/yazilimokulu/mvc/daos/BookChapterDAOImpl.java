package com.yazilimokulu.mvc.daos;


import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.BookChapter;
import com.yazilimokulu.utils.HibernateUtil;

@Repository
public class BookChapterDAOImpl extends AbstractDAO<BookChapter> implements BookChapterDAO  {

	@Override
	public List<BookChapter> findAll() {
		return super.findAll(BookChapter.class);
	}

	@Override
	public BookChapter find(Long id) {
		return find(BookChapter.class, id);
	}

	@Override
	public List<BookChapter> findByBookId(Long id) {
		try {
			startOperation();
			Query<BookChapter> query=session.createQuery("select c from BookChapter c where c.book.id=:bookId");
			query.setParameter("bookId", id);
			List<BookChapter> chapters=query.getResultList();
			tx.commit();
			return chapters;
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
		return null;
	}
	
	
}
