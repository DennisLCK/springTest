package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _03_listBooks.service.BookService;

// 依照書籍的書號來刪除一本書籍的紀錄。本控制器會呼叫 BookService介面的deleteBook()方法來完成。
@WebServlet("/_20_productMaintain/BookDelete.do")
public class BookDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String bId = request.getParameter("bookID");
		String bNo = request.getParameter("bookNo");
		int bookId = Integer.parseInt(bId);
//		BookService service = new BookServiceImpl();
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
										.getWebApplicationContext(sc);
		BookService service = ctx.getBean(BookService.class);
		int n = service.deleteBook(bookId);
		if (n == 1) {
			session.setAttribute("BookDeleteMsg", "書籍(" + bNo + ")刪除成功");
		} else {
			session.setAttribute("BookDeleteMsg", "書籍(" + bNo + ")刪除失敗");
		}
		response.sendRedirect("DisplayPageProducts");
		return;

	}

}
