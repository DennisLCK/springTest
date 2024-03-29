package _00_init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberBean;
import _03_listBooks.model.BookBean;
import _03_listBooks.model.CompanyBean;

public class EDMTableResetHibernate {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元

	public static void main(String args[]) {

		String line = "";
		int count = 0;
		String path = "C:\\_JSP\\Hibernate-workspace\\eDMoeeHS\\src\\main\\"
				+ "webapp\\WEB-INF\\applicationContext.xml";
	    ApplicationContext ctx = new FileSystemXmlApplicationContext(path);
	    SessionFactory factory = ((SessionFactory)ctx.getBean("sessionFactory"));
	    Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// 1. BookCompany表格
			// 1-2 由"data/bookCompany.dat"逐筆讀入BookCompany表格內的初始資料，
			// 然後依序新增到BookCompany表格中
			try (
				FileReader fr = new FileReader("data/bookCompany.dat"); 
				BufferedReader br = new BufferedReader(fr);
			) {
				while ((line = br.readLine()) != null) {
					if (line.startsWith(UTF8_BOM)) {
						line = line.substring(1);
					}

					String[] token = line.split("\\|");
					String name = token[0];
					String address = token[1];
					String url = token[2];
					CompanyBean cb = new CompanyBean(null, name, address, url);
					session.save(cb);
				}
				System.out.println("BookCompany 資料新增成功");
			} catch (IOException e) {
				System.err.println("新建BookCompany表格時發生IO例外: " + e.getMessage());
			}
			// 2. Book表格

			File file = new File("data/book.dat");
			// 2-1 由"data/book.dat"逐筆讀入Book表格內的初始資料，然後依序新增
			// 到Book表格中
			try (
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "UTF8");
				BufferedReader br = new BufferedReader(isr);
			) {
				while ((line = br.readLine()) != null) {
					System.out.println("line=" + line);
					// 去除 UTF8_BOM: \uFEFF
					if (line.startsWith(UTF8_BOM)) {
						line = line.substring(1);
					}
					String[] token = line.split("\\|");
					BookBean book = new BookBean();
					book.setTitle(token[0]);
					book.setAuthor(token[1]);
					book.setPrice(Double.parseDouble(token[2].trim()));
					book.setDiscount(Double.parseDouble(token[3].trim()));
					book.setCompanyId(Integer.parseInt(token[4].trim()));
					int companyId = Integer.parseInt(token[4].trim());
					CompanyBean cb = session.get(CompanyBean.class, companyId);
					book.setCompanyBean(cb);
					// 讀取圖片檔
					Blob blob = SystemUtils2018.fileToBlob(token[5].trim());
					book.setCoverImage(blob);
					book.setFileName(SystemUtils2018.extractFileName(token[5].trim()));
					book.setBookNo(token[6].trim());
					book.setStock(Integer.parseInt(token[7].trim()));
					book.setCategory(token[8].trim());
					session.save(book);
					System.out.println("新增一筆Book紀錄");
				}
				// 印出資料新增成功的訊息
				System.out.println("Book資料新增成功");
			}

			// 3. Member表格, 由"data/Input.txt"逐筆讀入Member表格內的初始資料，
			// 然後依序新增到Member表格中
			try (
				FileInputStream fis = new FileInputStream("data/Input.txt");
				InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr0);
			) {
				while ((line = br.readLine()) != null) {
					String[] sa = line.split("\\|");
					MemberBean bean = new MemberBean();
					bean.setMemberId(sa[0]);
					bean.setName(sa[1]);
					String pswd = GlobalService.getMD5Endocing(GlobalService.encryptString(sa[2]));
					bean.setPassword(pswd);
					bean.setAddress(sa[3]);
					bean.setEmail(sa[4]);
					bean.setTel(sa[5]);
					bean.setUserType(sa[6]);
					bean.setTotalAmt(Double.parseDouble(sa[7]));
					bean.setTs(new java.sql.Timestamp(System.currentTimeMillis()));
					// --------------處理Blob(圖片)欄位----------------
					Blob sb = SystemUtils2018.fileToBlob(sa[8]);
					bean.setMemberImage(sb);
					String imageFileName = sa[8].substring(sa[8].lastIndexOf("/") + 1);
					bean.setFileName(imageFileName);
					Clob clob = SystemUtils2018.fileToClob(sa[9]);
					bean.setComment(clob);
					bean.setUnpaid_amount(0.0);
					session.save(bean);
					count++;
					System.out.println("新增" + count + "筆記錄:" + sa[1]);
				}
				tx.commit();
			} catch (Exception ex) {
				tx.rollback();
				ex.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("新建表格時發生例外: " + e.getMessage());
			e.printStackTrace();
		}
		session.close();
		factory.close();
	}
}