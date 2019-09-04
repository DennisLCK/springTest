package _04_ShoppingCart.model.dao.impl;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.dao.OrderItemDao;
import _04_ShoppingCart.model.ude.ProductStockException;

/*
 * 一張合格的訂單必須經過下列檢查
 * 	1.	檢查訂購之商品的數量是否足夠。
 *      此功能寫在本類別的updateProductStock()方法內，參考該方法的說明。
 */
@Repository
public class OrderItemDaoImpl_Hibernate implements OrderItemDao {
	SessionFactory factory;
	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public OrderItemDaoImpl_Hibernate() {
//		factory = HibernateUtils.getSessionFactory();
	}

	/*
	 * 計算客戶欲購買之某項商品(以OrderItemBean物件oib來表示)的小計金額(subtotal)， 
	 *   計算公式為: 商品的數量 * 商品的單價 *  商品的折扣
	 */
	@Override
	public double findItemAmount(OrderItemBean oib) {
		double subtotal = oib.getQuantity() * oib.getUnitPrice() * oib.getDiscount();
		return subtotal;
	}

	@Override
	public int updateProductStock(OrderItemBean oib) {
		int n = 0;
		Integer stock = 0;
		Session session = factory.getCurrentSession();
		String hql0 = "SELECT stock FROM BookBean WHERE bookId = :bookId";
		String hql1 = "UPDATE BookBean SET stock = stock - :orderAmount WHERE bookId = :bookId";
		stock = (Integer) session.createQuery(hql0)
								 .setParameter("bookId", oib.getBookId())
								 .uniqueResult();
		if (stock == null) {
			stock = 0;
		}
		int stockLeft = stock - oib.getQuantity();
		if (stockLeft < 0) {
			throw new ProductStockException(
					"庫存數量不足: BookId: " + oib.getBookId() + ", 在庫量: " + stock + ", 訂購量: " 
					+ oib.getQuantity());
		}

		n = session.createQuery(hql1)
					.setParameter("bookId", oib.getBookId())
					.setParameter("orderAmount", oib.getQuantity())
					.executeUpdate();
		return n;
	}

	@Override
	public void setConnection(Connection conn) {
		throw new RuntimeException("本類別未實作此方法");
	}
}
