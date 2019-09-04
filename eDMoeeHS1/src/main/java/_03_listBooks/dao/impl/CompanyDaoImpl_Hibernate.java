package _03_listBooks.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _03_listBooks.dao.CompanyDao;
import _03_listBooks.model.CompanyBean;

// 本類別負責讀取資料庫內BookCompany表格內的紀錄
// 
@Repository
public class CompanyDaoImpl_Hibernate implements Serializable, CompanyDao {
	private static final long serialVersionUID = 1L;
	private String tagName = "";
	private int selected = -1;
	private int id = 0;
	SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	public CompanyDaoImpl_Hibernate() {
//		factory = HibernateUtils.getSessionFactory();
	}
	@Override
	public CompanyBean getCompanyById() {
		Session session = factory.getCurrentSession();
		CompanyBean cb = null;
		cb = session.get(CompanyBean.class, id);
		return cb;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyBean> getCompany() {
		List<CompanyBean> list = new ArrayList<>();
		String hql = "FROM CompanyBean";
		Session session = factory.getCurrentSession();
		list = session.createQuery(hql).list();

		return list;
	}

	@Override
	public int getSelected() {
		return selected;
	}

	@Override
	public void setSelected(int selected) {
		this.selected = selected;
	}

	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getSelectTag() {
		String ans = "";
		List<CompanyBean> cb = getCompany();
		ans += "<SELECT name='" + getTagName() + "'>";
		for (CompanyBean bean : cb) {
			int id = bean.getId();
			String name = bean.getName().substring(0, 4);
			if (id == selected) {
				ans += "<option value='" + id + "' selected>" + name + "</option>";
			} else {
				ans += "<option value='" + id + "'>" + name + "</option>";
			}
		}
		ans += "</SELECT>";
		return ans;
	}

}