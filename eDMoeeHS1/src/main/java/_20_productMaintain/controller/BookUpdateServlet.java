package _20_productMaintain.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _03_listBooks.model.BookBean;
import _03_listBooks.service.BookService;
import _03_listBooks.service.CompanyService;
@WebServlet("/_20_productMaintain/BookUpdate.do")
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
// 更新書籍資料，邏輯上與新增書籍資料類似
public class BookUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookBean bb ;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (!session.isNew())  {
			bb = (BookBean) session.getAttribute("bean");
		} else {
			bb = new BookBean();
		}
		String pageNo = "1";
		Map<String, String> errorMsgs = new HashMap<String, String>();
		Map<String, String> successMsgs = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("ErrMsg", errorMsgs);
		session.setAttribute("successMsg", successMsgs);
		try {
			String title = "";
			String author = "";
			String priceStr = "";
			double price = 0;
			String bookNo = "";
			String companyID = "";
			String category = "";
			String fileName = "";
			long sizeInBytes = 0;
			InputStream is = null;
			// request.getParts()方法傳回一個由javax.servlet.http.Part物件所組成的Collection
			// javax.servlet.http.Part: 代表上傳到Server的，可以是正常的表單資料(form data)，
			// 也可以上傳的檔案。
			// Part介面可以: 
			// 1. 傳回欄位的名稱(<input>的name屬性)、大小、ContentType
			// 2. 每個Part的Header
			// 3. 刪除Part
			// 4. 將Part寫入硬碟
			Collection<Part> parts = request.getParts();
			//GlobalService.exploreParts(parts, request);
			
			if (parts != null) {   // 如果這是一個上傳資料的表單				
				for (Part p : parts) {
					String fldName = p.getName();
					String value = request.getParameter(fldName);
					if (p.getContentType() == null) {
						if (fldName.equals("title")) {
							title = value;
							bb.setTitle(title);
							if (value == null || title.trim().length() == 0) {
								errorMsgs.put("errTitle", "必須輸入書名");
							} else {
								request.setAttribute("title", title);
							}
						} else if (fldName.equals("author")) {
							author = value;
							bb.setAuthor(author);
							if (author == null || author.trim().length() == 0) {
								errorMsgs.put("errAuthor", "必須輸入作者");
							} else {
								request.setAttribute("author", author);
							}
						} else if (fldName.equals("price")) {
							priceStr = value;
							priceStr = priceStr.trim();
							bb.setPriceStr(priceStr);
							if (priceStr == null
									|| priceStr.trim().length() == 0) {
								errorMsgs.put("errPrice", "必須輸入價格");
							} else {
								try {
									price = Double.parseDouble(priceStr);
								} catch (NumberFormatException e) {
									errorMsgs.put("errPrice", "價格必須是數值");
								}
							}
							request.setAttribute("price", priceStr);
						} else if (fldName.equals("bookNo")) {
							bookNo = value;
							bb.setBookNo(bookNo);
							if (bookNo == null || bookNo.trim().length() == 0) {
								errorMsgs.put("errBookNo", "必須輸入書號");
							} else {
								request.setAttribute("bookNo", bookNo);
							}
						} else if (fldName.equals("companyID")) {
							companyID = value;
							if (companyID == null
									|| companyID.trim().length() == 0) {
								errorMsgs.put("errCompanyID", "必須輸入出版社");
							}
							//request.setAttribute("cID", companyID);
						} else if (fldName.equals("pageNo")){
							pageNo = value;
						} else if (fldName.equals("category")) {
							category = value;
							;
							if (category == null || category.trim().length() == 0) {
								errorMsgs.put("errCategory", "必須輸入類型");
							}
							request.setAttribute("category", category);
						}


					} else {
						fileName = getFileName(p); // 此為圖片檔的檔名
						fileName = GlobalService.getFileName(p); // 由變數 p 中取出檔案名稱
						fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
						if (fileName != null && fileName.trim().length() > 0) {
							sizeInBytes = p.getSize();
							is = p.getInputStream();
						} else {
							sizeInBytes = -1;
						}
					} 
				}
			} else {
				errorMsgs.put("errTitle", "此表單不是上傳檔案的表單");
			}
			if (!errorMsgs.isEmpty()) {
			   RequestDispatcher rd = request.getRequestDispatcher("BookUpdate.jsp");
			   rd.forward(request, response);
			   return;
			} 
			
//			BookService bookService = new BookServiceImpl();
//			CompanyService cbDao = new CompanyServiceImpl();  
			ServletContext sc = getServletContext();
			WebApplicationContext ctx = WebApplicationContextUtils
											.getWebApplicationContext(sc);
			BookService bookService = ctx.getBean(BookService.class);
			CompanyService cbDao = ctx.getBean(CompanyService.class);
			int cId  = Integer.parseInt(companyID);
			cbDao.setId(cId);
			//CompanyBean cb = cbDao.getCompanyById();
			
			Blob blob = null;
			if (sizeInBytes != -1){
				blob = SystemUtils2018.fileToBlob(is, sizeInBytes);
			}
			BookBean newBean = new BookBean(bb.getBookId(), title, 
					author, price, 1.0, fileName, bookNo, blob, cId, category);
			bookService.updateBook(newBean, sizeInBytes); 
			RequestDispatcher rd = request.getRequestDispatcher("DisplayPageProducts?pageNo=" + pageNo);
			rd.forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.put("errDBMessage", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("BookUpdate.jsp");
			rd.forward(request, response);
		}
	}
	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}
