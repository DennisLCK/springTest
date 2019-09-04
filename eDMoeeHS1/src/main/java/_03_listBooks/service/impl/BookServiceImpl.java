package _03_listBooks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _03_listBooks.dao.BookDao;
import _03_listBooks.model.BookBean;
import _03_listBooks.service.BookService;
@Service
public class BookServiceImpl implements BookService {

	BookDao dao;
	@Autowired
	public void setDao(BookDao dao) {
		this.dao = dao;
	}
	
//	SessionFactory factory;
	public BookServiceImpl() {
//		dao = new BookDaoImpl_Hibernate();
//		factory = HibernateUtils.getSessionFactory();
	}
	@Transactional
	@Override
	public int getTotalPages() {
		// DB-Accessed
		int n = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			n = dao.getTotalPages();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return n;
	}
	@Transactional
	@Override
	// DB-Accessed
	public List<BookBean> getPageBooks() {
		List<BookBean> list = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getPageBooks();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return list;

	}

	@Override
	// Without DB-Access
	public int getPageNo() {
		return dao.getPageNo();
	}

	@Override
	// Without DB-Access
	public void setPageNo(int pageNo) {
		dao.setPageNo(pageNo);
	}

	@Override
	public int getRecordsPerPage() {
		return dao.getRecordsPerPage();
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		dao.setRecordsPerPage(recordsPerPage);
	}

	@Override
	public long getRecordCounts() {
		return dao.getRecordCounts();
	}
	@Transactional
	@Override
	public BookBean getBook(int bookId) {
		BookBean bean = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			bean = dao.getBook(bookId);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return bean;
	}
	@Transactional
	@Override
	public int updateBook(BookBean bean, long sizeInBytes) {
		int n = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			n = dao.updateBook(bean, sizeInBytes);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return n;
	}
	@Transactional	
	@Override
	public int deleteBook(int no) {
		int n = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			n = dao.deleteBook(no);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return n;
	}
	@Transactional		
	@Override
	public int saveBook(BookBean bean) {
		int n = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			n = dao.saveBook(bean);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return n;
	}
	@Transactional	
	@Override
	public List<String> getCategory() {
		List<String> list = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getCategory();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return list;
	}

	@Override
	public void setSelected(String category) {
		dao.setSelected(category);
	}
	@Transactional	
	@Override
	public String getCategoryTag() {
		String tag = "";
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			tag = dao.getCategoryTag();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return tag;
	}
}
