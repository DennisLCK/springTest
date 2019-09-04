package _00_init.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _00_init.util.HibernateUtils;

//@WebFilter("/_05_orderProcess/orderDetail.do")
public class LazyLoadingFilter implements Filter {
	
	SessionFactory factory;

	public void destroy() {
		factory.close();
	}

	public LazyLoadingFilter() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chain.doFilter(request, response);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
			throw new RuntimeException("LazyLoadingFIlter捕捉例外: " + ex.getMessage());
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		factory = HibernateUtils.getSessionFactory();
	}

}
