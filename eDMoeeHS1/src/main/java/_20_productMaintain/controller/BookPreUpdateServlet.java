package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _03_listBooks.model.BookBean;
import _03_listBooks.service.BookService;
import _03_listBooks.service.CompanyService;
import _03_listBooks.service.impl.CompanyServiceImpl;

@WebServlet("/_20_productMaintain/BookPreUpdate.do")
public class BookPreUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
		     response.sendRedirect(request.getContextPath() + "/index.jsp");
		     return;
		}
		int bookId = 0;
		String strBookId = request.getParameter("BOOKID");
		
		if (strBookId != null) {
			bookId = Integer.parseInt(strBookId);
		}
		
//		BookService bookService = new BookServiceImpl();
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
										.getWebApplicationContext(sc);
		BookService bookService = ctx.getBean(BookService.class);
		BookBean bean = bookService.getBook(bookId);
		session.setAttribute("bean", bean);

		bookService.setSelected(bean.getCategory());
		String categoryTag = bookService.getCategoryTag();
		request.setAttribute("SelectCategoryTag", categoryTag);
//		CompanyService cs = new CompanyServiceImpl();
		CompanyService cs = ctx.getBean(CompanyService.class);
		cs.setSelected(bean.getCompanyBean().getId());
		cs.setTagName("companyID");
		String companyTag = cs.getSelectTag();
		request.setAttribute("SelectCompanyTag", companyTag);

		RequestDispatcher rd = request.getRequestDispatcher("/_20_productMaintain/BookUpdate.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
}
