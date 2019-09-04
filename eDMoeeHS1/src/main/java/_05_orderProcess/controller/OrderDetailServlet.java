package _05_orderProcess.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _04_ShoppingCart.model.OrderBean;
import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.service.OrderService;
import _04_ShoppingCart.model.service.impl.OrderServiceImpl;

// 依照會員編號與訂單編號來讀取某筆訂單的所有資料，這些資料將封裝為一個OrderBean物件

@WebServlet("/_05_orderProcess/orderDetail.do")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderNo = request.getParameter("orderNo");
		int no = Integer.parseInt(orderNo.trim());

//		OrderService orderService = new OrderServiceImpl();
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
										.getWebApplicationContext(sc);
		OrderService orderService = ctx.getBean(OrderService.class);
		OrderBean ob = orderService.getOrder(no);
		request.setAttribute("OrderBean", ob);   // 將OrderBean物件暫存到請求物件內
		RequestDispatcher rd = request.getRequestDispatcher("ShowOrderDetail.jsp");
		rd.forward(request, response);
		return;
	}

	public void displayOrderBean(OrderBean ob) {
		System.out.println("ob.getOrderNo()=" + ob.getOrderNo());
		System.out.println("ob.getMemberId()=" + ob.getMemberId());
		System.out.println("ob.getOrderDate=" + ob.getOrderDate());
		System.out.println("ob.getTotalAmount=" + ob.getTotalAmount());
		System.out.println("ob.getInvoiceTitle=" + ob.getInvoiceTitle());
		System.out.println("ob.getBNO=" + ob.getBno());
		System.out.println("ob.getShippingAddress=" + ob.getShippingAddress());
		System.out.println("ob.getCancelTag=" + ob.getCancelTag());
		System.out.println("==============訂單明細=================");
		Set<OrderItemBean> items = ob.getItems();
		for (OrderItemBean oib : items) {
			System.out.println("---------------一筆明細---------------");
			System.out.println("   oib.getSeqno()=" + oib.getSeqno());
			System.out.println("   oib.getOrderNo()=" + oib.getOrderNo());
			System.out.println("   oib.getBookId()=" + oib.getBookId());
			System.out.println("   oib.getDescription()=" + oib.getDescription());
			System.out.println("   oib.getQuantity()=" + oib.getQuantity());
			System.out.println("   oib.getUnitPrice()=" + oib.getUnitPrice());
			System.out.println("   oib.getDiscount()=" + oib.getDiscount());
		}
	}
}