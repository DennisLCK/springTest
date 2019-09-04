package _03_listBooks.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import _00_init.util.GlobalService;
import _03_listBooks.dao.BookDao;
import _03_listBooks.dao.CompanyDao;
import _03_listBooks.model.BookBean;
import _03_listBooks.model.CompanyBean;
@Repository
public class BookDaoImpl_Hibernate implements Serializable, BookDao {

	private static final long serialVersionUID = 1L;
	private int pageNo = 0;		// 存放目前顯示之頁面的編號
	private int recordsPerPage = GlobalService.RECORDS_PER_PAGE; // 預設值：每頁三筆
	private int totalPages = -1;
	
	String selected = "";
	SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	CompanyDao  dao;
	@Autowired
	public void setDao(CompanyDao dao) {
		this.dao = dao;
	}
	
	public BookDaoImpl_Hibernate() {
//        factory = HibernateUtils.getSessionFactory();
    }
	
	// 計算販售的商品總共有幾頁
	@Override
	public int getTotalPages() {
		// 注意下一列敘述的每一個型態轉換
		totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));

		return totalPages;
	}
	
	// 查詢某一頁的商品(書籍)資料，執行本方法前，一定要先設定實例變數pageNo的初值
	@SuppressWarnings("unchecked")
	@Override
	public List<BookBean> getPageBooks() {
		List<BookBean> list = new ArrayList<BookBean>();
        String hql = "FROM BookBean";
        Session session = factory.getCurrentSession();

        int startRecordNo = (pageNo - 1) * recordsPerPage;

        list = session.createQuery(hql)
                      .setFirstResult(startRecordNo)
                      .setMaxResults(recordsPerPage)
                      .list();
        return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getRecordCounts() {
		long count = 0; // 必須使用 long 型態
        String hql = "SELECT count(*) FROM BookBean";
        Session session = factory.getCurrentSession();
        List<Long> list = session.createQuery(hql).list();
        if (list.size() > 0) {
            count = list.get(0);
        }
        return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategory() {
		String hql = "SELECT DISTINCT category FROM BookBean";
        Session session = factory.getCurrentSession();
        List<String> list = null;
        list = session.createQuery(hql).list();
        return list;
	}
	
	@Override
	public String getCategoryTag() {
		String ans = "";
		List<String> list = getCategory();
		ans += "<SELECT name='category'>";
		for (String cate : list) {
			if (cate.equals(selected)) {
				ans += "<option value='" + cate + "' selected>" + cate + "</option>";
			} else {
				ans += "<option value='" + cate + "'>" + cate + "</option>";
			}
		}
		ans += "</SELECT>";
		return ans;
	}
	
	// 修改一筆書籍資料
	@Override
	public int updateBook(BookBean bean, long sizeInBytes) {
		int n = 0;
        if (bean.getCompanyBean() == null) {
//            CompanyDao dao = new CompanyDaoImpl_Hibernate();  
            dao.setId(bean.getCompanyId());
            CompanyBean cb = dao.getCompanyById();
            bean.setCompanyBean(cb);
        }

        if (sizeInBytes == -1) { // 不修改圖片
            n = updateBook(bean);
            return n;
        }
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(bean);
        n++;
        return n;
	}

	// 修改一筆書籍資料，不改圖片
	public int updateBook(BookBean bean) {
		int n = 0;
        BookBean b0 = null;
        Session session = factory.getCurrentSession();
        b0 = session.get(BookBean.class, bean.getBookId());
        bean.setStock(b0.getStock());
        bean.setPriceStr(b0.getPriceStr());
        bean.setCoverImage(b0.getCoverImage());
        bean.setFileName(b0.getFileName());
        session.evict(b0);
        session.saveOrUpdate(bean);
        n++;
        return n;
	}

	// 依bookID來刪除單筆記錄
	@Override
	public int deleteBook(int no) {
		int n = 0;
        Session session = factory.getCurrentSession();
        BookBean bb = new BookBean();
        bb.setBookId(no);
        session.delete(bb);
        n++;
        return n;

	}

	// 新增一筆記錄---
	@Override
	public int saveBook(BookBean bean) {
		int n = 0;
        Session session = factory.getCurrentSession();
        if (bean.getCompanyBean() == null) {
//            CompanyDao dao = new CompanyDaoImpl_Hibernate();   
            dao.setId(bean.getCompanyId());
            CompanyBean cb = dao.getCompanyById();
            bean.setCompanyBean(cb);
        }
        session.save(bean);
        n++;
        return n;
	}

	@Override
	public void setSelected(String selected) {
		this.selected = selected;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	@Override
	public BookBean getBook(int bookId) {
		BookBean bean = null;
        Session session = factory.getCurrentSession();
        bean = session.get(BookBean.class, bookId);
        return bean;
	}
}