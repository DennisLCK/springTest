package _00_init.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _01_register.model.MemberBean;
import _01_register.service.MemberService;
import _01_register.service.impl.MemberServiceImpl;
import _03_listBooks.model.BookBean;
import _03_listBooks.service.BookService;

@WebServlet("/_00_init/getImage")
public class RetrieveImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream os = null;
		InputStream is = null;
		String fileName = null;
		String mimeType = null;
		Blob blob = null;
		try {
			// 讀取瀏覽器傳送來的主鍵
			String id = request.getParameter("id");
			// 讀取瀏覽器傳送來的type，以分辨要處理哪個表格
			String type = request.getParameter("type"); 
			switch(type.toUpperCase()){
				case "BOOK":
					//BookService bookService = new BookServiceImpl();
					ServletContext sc = getServletContext();
					WebApplicationContext ctx = WebApplicationContextUtils
													.getWebApplicationContext(sc);
					BookService bookService = ctx.getBean(BookService.class);
					int nId = 0;
					try {
						nId = Integer.parseInt(id);
					} catch(NumberFormatException ex) {
						;
					}
					BookBean bean1 = bookService.getBook(nId);
					if (bean1 != null) {
						blob = bean1.getCoverImage();
						if (blob != null) {
							is = blob.getBinaryStream();
						}
						fileName = bean1.getFileName();
					}
					break;
				case "MEMBER":
					//MemberService memberService = new MemberServiceImpl();
					ServletContext sc2 = getServletContext();
					WebApplicationContext ctx2 = WebApplicationContextUtils
												 .getWebApplicationContext(sc2);
					MemberService memberService = ctx2.getBean(MemberService.class);
					MemberBean bean2 = memberService.queryMember(id);
					if (bean2 != null) {
						blob = bean2.getMemberImage();
						if (blob != null) { 
							is = blob.getBinaryStream();
						}
						fileName = bean2.getFileName();
					}
					break;
			}

			// 如果圖片的來源有問題，就送回預設圖片(/images/NoImage.png)	
			if (is == null) {
				fileName = "NoImage.png" ; 
				is = getServletContext().getResourceAsStream(
						"/images/" + fileName);
			}
			
			// 由圖片檔的檔名來得到檔案的MIME型態
			mimeType = getServletContext().getMimeType(fileName);
			// 設定輸出資料的MIME型態
			response.setContentType(mimeType);
			// 取得能寫出非文字資料的OutputStream物件
			os = response.getOutputStream();	
			// 由InputStream讀取位元組，然後由OutputStream寫出
			int len = 0;
			byte[] bytes = new byte[8192];
			while ((len = is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("_00_init.util.RetrieveImageServlet#doGet()發生SQLException: " + ex.getMessage());
		} finally{
			if (is != null) 
				is.close();
			if (os != null) 
				os.close();
			
		}
	}
}