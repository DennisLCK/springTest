package _02_login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import _00_init.util.GlobalService;
import _01_register.model.MemberBean;
import _01_register.service.MemberService;

@WebServlet("/_02_login/login2.do")
public class LoginServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		request.setAttribute("ErrorMsgKey", errorMsgMap);
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("pswd");
		String requestURI = (String) session.getAttribute("requestURI");

		if (userId == null || userId.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "帳號欄必須輸入");
		}
		if (password == null || password.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "密碼欄必須輸入");
		}
		if (!errorMsgMap.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("login2.jsp");
			rd.forward(request, response);
			return;
			
		}
		ServletContext sc = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		MemberService memberService = ctx.getBean(MemberService.class);
		password = GlobalService.getMD5Endocing(GlobalService.encryptString(password));
		MemberBean mb = null;
		try {
			
			mb = memberService.checkIDPassword(userId, password);
			if (mb != null) {
				session.setAttribute("LoginOK", mb);
			} else {
				errorMsgMap.put("LoginError", "該帳號不存在或密碼錯誤");
			}
		} catch (RuntimeException ex) {
			errorMsgMap.put("LoginError", ex.getMessage());
		}
		
		if (errorMsgMap.isEmpty()) {
			if (requestURI != null) {
				System.out.println("requestURI=" + requestURI + "*");
				requestURI = (requestURI.length() == 0 ? request.getContextPath() : requestURI);
				response.sendRedirect(response.encodeRedirectURL(requestURI));
				return;
			} else {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
				return;
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login2.jsp");
			rd.forward(request, response);
			return;
		}
	}
}
