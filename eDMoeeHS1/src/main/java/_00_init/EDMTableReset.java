package _00_init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import _00_init.util.DBService;
import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberBean;
import _03_listBooks.model.BookBean;
import _03_listBooks.model.CompanyBean;

public class EDMTableReset {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元

	public static void main(String args[]) {

		String line = "";

		int count = 0;
		try (
			Connection con = DriverManager.getConnection(DBService.getDbUrl(), DBService.getUser(),
			DBService.getPassword()); Statement stmt = con.createStatement();
		) {
			// 1. Book表格
			stmt.executeUpdate(DBService.getDropBook());
			System.out.println("Book表格刪除成功");
			stmt.executeUpdate(DBService.getCreateBook());
			System.out.println("Book表格產生成功");

			File file = new File("data/book.dat");
			// 1-2 由"data/book.dat"逐筆讀入Book表格內的初始資料，然後依序新增
			// 到Book表格中
			try (FileInputStream fis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(fis, "UTF8");
					BufferedReader br = new BufferedReader(isr);) {
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
					// 讀取圖片檔
					Blob blob = SystemUtils2018.fileToBlob(token[5].trim());
					book.setCoverImage(blob);
					book.setFileName(SystemUtils2018.extractFileName(token[5].trim()));
					book.setBookNo(token[6].trim());
					book.setStock(Integer.parseInt(token[7].trim()));
					book.setCategory(token[8].trim());
					int n = saveBook(book, con);
					System.out.println("新增一筆Book紀錄是否成功=" + n);
				}
				// 印出資料新增成功的訊息
				System.out.println("Book資料新增成功");
			}
			// 2. BookCompany表格
			stmt.executeUpdate(DBService.getDropBookCompany());
			System.out.println("BookCompany表格刪除成功");
			stmt.executeUpdate(DBService.getCreateBookCompany());
			System.out.println("BookCompany表格產生成功");
			// 2-2 由"data/bookCompany.dat"逐筆讀入BookCompany表格內的初始資料，
			// 然後依序新增到BookCompany表格中
			try (FileReader fr = new FileReader("data/bookCompany.dat"); BufferedReader br = new BufferedReader(fr);) {
				while ((line = br.readLine()) != null) {
					if (line.startsWith(UTF8_BOM)) {
						line = line.substring(1);
					}

					String[] token = line.split("\\|");
					String name = token[0];
					String address = token[1];
					String url = token[2];
					CompanyBean cb = new CompanyBean(null, name, address, url);

					saveCompany(cb, con);
				}
				System.out.println("BookCompany 資料新增成功");
			} catch (IOException e) {
				System.err.println("新建BookCompany表格時發生IO例外: " + e.getMessage());
			}

			// 3. Member表格
			stmt.executeUpdate(DBService.getDropMember());
			System.out.println("Member表格刪除成功");
			stmt.executeUpdate(DBService.getCreateMember());
			System.out.println("Member表格產生成功");
			// 3-2 由"data/Input.txt"逐筆讀入Member表格內的初始資料，
			// 然後依序新增到Member表格中
			try (FileInputStream fis = new FileInputStream("data/Input.txt");
					InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
					BufferedReader br = new BufferedReader(isr0);) {
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
					saveMember(bean, con);
					count++;
					System.out.println("新增" + count + "筆記錄:" + sa[1]);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// 4. 定義Orders表格
			try {
				// 執行刪除Orders表格的SQL命令
				stmt.executeUpdate(DBService.getDropOrders());
				System.out.println("Orders表格刪除成功");
				stmt.executeUpdate(DBService.getCreateOrders());
				System.out.println("Orders表格產生成功");
			} catch (SQLException e) {
				System.err.println("刪除Orders表格時發生SQL例外: " + e.getMessage());
			}
			// 刪除OrderItems表格
			// 定義OrderItems表格
			try {
				stmt.executeUpdate(DBService.getDropOrderItems());
				System.out.println("OrderItems表格刪除成功");
				stmt.executeUpdate(DBService.getCreateOrderItems());
				System.out.println("OrderItems表格產生成功");
			} catch (SQLException e) {
				System.err.println("刪除OrderItems表格時發生SQL例外: " + e.getMessage());

			}
		} catch (SQLException e) {
			System.err.println("新建表格時發生SQL例外: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("新建表格時發生IO例外: " + e.getMessage());
		}

	}

	public static int saveBook(BookBean bean, Connection con) {
		int n = 0;
		String sqlS = "INSERT INTO Book " + " (title,  author,  price, discount, "
				+ " companyId, fileName, bookNo, coverImage, stock, category) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pStmt = con.prepareStatement(sqlS);) {
			pStmt.setString(1, bean.getTitle());
			pStmt.setString(2, bean.getAuthor());
			pStmt.setDouble(3, bean.getPrice());
			pStmt.setDouble(4, bean.getDiscount());
			pStmt.setInt(5, bean.getCompanyId());
			pStmt.setString(6, bean.getFileName());
			pStmt.setString(7, bean.getBookNo());
			pStmt.setBlob(8, bean.getCoverImage());
			pStmt.setInt(9, bean.getStock());
			pStmt.setString(10, bean.getCategory());
			n = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return n;
	}

	static public int saveCompany(CompanyBean cb, Connection con) {
		String sqlS = "INSERT INTO BookCompany (name, address, url) VALUES(?, ?, ?)";
		int n = 0;
		try (

				PreparedStatement ps = con.prepareStatement(sqlS);) {
			ps.setString(1, cb.getName());
			ps.setString(2, cb.getAddress());
			ps.setString(3, cb.getUrl());
			n = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return n;
	}

	static public int saveMember(MemberBean mb, Connection con) {
		String sql = "insert into Member " + " (memberID, name, password, address, email, "
				+ " tel, userType, registerTime, totalAmt, memberImage," + " fileName, comment, unpaid_amount) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int n = 0;
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, mb.getMemberId());
			ps.setString(2, mb.getName());
			ps.setString(3, mb.getPassword());
			ps.setString(4, mb.getAddress());
			ps.setString(5, mb.getEmail());
			ps.setString(6, mb.getTel());
			ps.setString(7, mb.getUserType());
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(8, now);
			ps.setDouble(9, mb.getTotalAmt());
			ps.setBlob(10, mb.getMemberImage());
			ps.setString(11, mb.getFileName());
			ps.setClob(12, mb.getComment());
			ps.setDouble(13, mb.getUnpaid_amount());
			n = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return n;
	}
}