package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _03_listBooks.service.BookService;
import _03_listBooks.service.CompanyService;
import _03_listBooks.service.impl.BookServiceImpl;

@WebServlet("/_20_productMaintain/BookPreInsert.do")
public class BookPreInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 準備新增書籍資料所需要的出版社與書籍類型的資料
//		CompanyService cs = new CompanyServiceImpl();
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
										.getWebApplicationContext(sc);
		CompanyService cs = ctx.getBean(CompanyService.class);

		cs.setTagName("companyID");
		String companyTag = cs.getSelectTag();
		request.setAttribute("SelectCompanyTag", companyTag);

		// 本類別負責讀取資料庫內Book表格內某一頁的紀錄，並能新增紀錄、修改紀錄、刪除記錄等
//		BookService bookService = new BookServiceImpl();
		BookService bookService = ctx.getBean(BookService.class);
		String categoryTag = bookService.getCategoryTag();
		request.setAttribute("SelectCategoryTag", categoryTag);

		request.setAttribute("baBean", bookService);
		RequestDispatcher rd = request.getRequestDispatcher("/_20_productMaintain/BookInsert.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
