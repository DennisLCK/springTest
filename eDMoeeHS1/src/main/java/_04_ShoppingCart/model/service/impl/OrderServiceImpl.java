package _04_ShoppingCart.model.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _01_register.dao.MemberDao;
import _04_ShoppingCart.model.OrderBean;
import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.dao.OrderDao;
import _04_ShoppingCart.model.dao.OrderItemDao;
import _04_ShoppingCart.model.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
//	private SessionFactory factory;
	private OrderItemDao oidao;
	@Autowired
	public void setOidao(OrderItemDao oidao) {
		this.oidao = oidao;
	}
	private MemberDao mdao;
	@Autowired
	public void setMdao(MemberDao mdao) {
		this.mdao = mdao;
	}
	private OrderDao odao;
	@Autowired
	public void setOdao(OrderDao odao) {
		this.odao = odao;
	}
	public OrderServiceImpl() {
//		factory = HibernateUtils.getSessionFactory();
//        oidao = new OrderItemDaoImpl_Hibernate();
//        odao  = new OrderDaoImpl_Hibernate();
//        mdao  = new MemberDaoImpl_Hibernate();
	}

	@Transactional
	@Override
	// 這是一個交易
	public void persistOrder(OrderBean ob) {
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Session session = factory.getCurrentSession();
		// 檢查並更新會員的未付款餘額
		mdao.updateUnpaidOrderAmount(ob);
		// 檢查每筆訂單明細所訂購之商品的庫存數量是否足夠
		checkStock(ob);
		// 儲存該筆訂單
		odao.insertOrder(ob);
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null)  tx.rollback();
//            System.out.println("發生異常，交易回滾.....,原因: " + e.getMessage());
//            throw new RuntimeException(e);
//        } 
	}

	public void checkStock(OrderBean ob) {
		Set<OrderItemBean> items = ob.getItems();
		for (OrderItemBean oib : items) {
			oidao.updateProductStock(oib);
		}
	}

	public OrderDao getOdao() {
		return odao;
	}

//	@Override
//    // 本方法為過渡版本
//    public OrderBean getOrder(int orderNo) {
//        OrderBean  bean = null;
//        Session session = factory.getCurrentSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            bean = odao.getOrder(orderNo);
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) tx.rollback();
//            throw new RuntimeException(e);
//        } 
//        return bean;
//    }
	@Override
	// 本方法將由控制 Lazy Loading 的過濾器之doFilter()方法間接呼叫，所以不可以在此方法內執行與交易
	// 有關的方法
	@Transactional
	public OrderBean getOrder(int orderNo) {
		OrderBean bean = null;
		// Session session = factory.getCurrentSession();
		// Transaction tx = null;
		// try {
		// tx = session.beginTransaction();
		bean = odao.getOrder(orderNo);
		// tx.commit();
		// } catch (Exception e) {
		// if (tx != null) tx.rollback();
		// throw new RuntimeException(e);
		// }
		return bean;
	}

	@Transactional
	@Override
	public List<OrderBean> getAllOrders() {
		List<OrderBean> list = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			list = odao.getAllOrders();
//			tx.commit();
//		} catch (Exception e) {
//			if (tx != null)  tx.rollback();
//			throw new RuntimeException(e);
//		}
		return list;
	}

	@Transactional	
	@Override
	public List<OrderBean> getMemberOrders(String memberId) {
		List<OrderBean> list = null;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			list = odao.getMemberOrders(memberId);
//			tx.commit();
//		} catch (Exception e) {
//			if (tx != null)  tx.rollback();
//			throw new RuntimeException(e);
//		}
		return list;
	}
}
