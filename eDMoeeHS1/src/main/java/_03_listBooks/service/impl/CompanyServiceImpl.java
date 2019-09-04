package _03_listBooks.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _00_init.util.HibernateUtils;
import _03_listBooks.dao.CompanyDao;
import _03_listBooks.dao.impl.CompanyDaoImpl_Hibernate;
import _03_listBooks.model.CompanyBean;
import _03_listBooks.service.CompanyService;

// 本類別負責讀取資料庫內eBookCompany表格內的紀錄
// 
@Service
public class CompanyServiceImpl implements Serializable, CompanyService {
	private static final long serialVersionUID = 1L;

	CompanyDao dao;
	
	@Autowired
	public void setDao(CompanyDao dao) {
		this.dao = dao;
	}
//	SessionFactory factory = null;

	public CompanyServiceImpl() {
//		dao = new CompanyDaoImpl_Hibernate();
//		factory = HibernateUtils.getSessionFactory();
	}
	@Transactional
	public List<CompanyBean> getCompany() {
		List<CompanyBean> list = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getCompany();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return list;
	}
	@Transactional
	public CompanyBean getCompanyById() {
		CompanyBean bean = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			bean = dao.getCompanyById();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return bean;
	}
	@Transactional
	public String getSelectTag() {
		String segment = "";
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			segment = dao.getSelectTag();
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return segment;
	}

	// Without DB-Access
	public int getSelected() {
		return dao.getSelected();
	}

	// Without DB-Access
	public void setSelected(int selected) {
		dao.setSelected(selected);
	}

	// Without DB-Access
	public String getTagName() {
		return dao.getTagName();
	}

	// Without DB-Access
	public void setTagName(String tagName) {
		dao.setTagName(tagName);
	}

	// Without DB-Access
	public int getId() {
		return dao.getId();
	}

	// Without DB-Access
	public void setId(int id) {
		dao.setId(id);
	}

}