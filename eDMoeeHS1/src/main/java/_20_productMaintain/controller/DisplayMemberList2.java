package _20_productMaintain.controller;

import java.io.IOException;
import java.util.List;

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

import _01_register.model.MemberBean;
import _01_register.service.MemberService;
import _01_register.service.impl.MemberServiceImpl;

@WebServlet("/_20_productMaintain/DisplayMemberList2")
public class DisplayMemberList2 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	int pageNo = 1;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();
	
		if (session == null || session.isNew()) {
			// 請使用者登入
			response.sendRedirect(response.encodeRedirectURL("../_02_login/login.jsp"));
			return;
		}
		session.setAttribute("requestURI", requestURI);
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		if (mb == null) {
			response.sendRedirect(response.encodeRedirectURL("../_02_login/login_p1.jsp"));
			return;
		}
		String pageNoStr = request.getParameter("pageNo");
		if (pageNoStr == null) {
			pageNo = 1;
		} else {
			try {
				pageNo = Integer.parseInt(pageNoStr.trim());
			} catch (NumberFormatException e) {
				pageNo = 1;
			}
		}
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
										.getWebApplicationContext(sc);
		MemberService ms = new MemberServiceImpl();
		
		RequestDispatcher rd = request.getRequestDispatcher("showMembers.jsp");
		rd.forward(request, response);
		return;		
		
	}
	
}
