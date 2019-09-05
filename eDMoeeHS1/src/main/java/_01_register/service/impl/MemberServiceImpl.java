package _01_register.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _01_register.dao.MemberDao;
import _01_register.dao.impl.MemberDaoImpl_Hibernate;
import _01_register.model.MemberBean;
import _01_register.service.MemberService;
import _04_ShoppingCart.model.OrderBean;
@Service
public class MemberServiceImpl implements MemberService {

	MemberDao dao;
	@Autowired
	public void setDao(MemberDao dao) {
		this.dao = dao;
	}

//	SessionFactory factory;
//	@Autowired
//	public void setFactory(SessionFactory factory) {
//		this.factory = factory;
//	}
	
	public MemberServiceImpl() {
	}
	@Transactional
	@Override
	public int saveMember(MemberBean mb) {
		int count = 0;

//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			dao.saveMember(mb);
			count++;
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return count;
	}
	@Transactional
	@Override
	public boolean idExists(String id) {
		boolean exist = false;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			exist = dao.idExists(id);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return exist;
	}
	@Transactional
	@Override
	public MemberBean queryMember(String id) {
		MemberBean mb = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			mb = dao.queryMember(id);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null) tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return mb;
	}
	@Transactional
	@Override
	public void updateUnpaidOrderAmount(OrderBean ob) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			dao.updateUnpaidOrderAmount(ob);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
	}
	@Transactional
	public MemberBean checkIDPassword(String userId, String password) {
		MemberBean mb = null;

//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			mb = dao.checkIDPassword(userId, password);
//			tx.commit();
//		} catch (Exception ex) {
//			if (tx != null)  tx.rollback();
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
		return mb;
	}

	@Override
	public List<MemberBean> getAllMembers() {
		MemberDao dao = new MemberDaoImpl_Hibernate();
		return dao.getAllMembers();
	}

	@Override
	public MemberBean getMember(int pkey) {
		MemberDao dao = new MemberDaoImpl_Hibernate();
		return dao.getMember(pkey);
	}

	@Override
	public int deleteMember(int pkey) {
		MemberDao dao = new MemberDaoImpl_Hibernate();
		return dao.deleteMember(pkey);
	}

	@Override
	public int updateMember(MemberBean mb) {
		MemberDao dao = new MemberDaoImpl_Hibernate();
		return dao.updateMember(mb);
	}

	
}


