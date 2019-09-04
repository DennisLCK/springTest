package _03_listBooks.dao;

import java.util.List;

import _03_listBooks.model.BookBean;

public interface BookDao {
	// 依bookID來刪除單筆記錄
	int deleteBook(int no);

	// 依bookID來查詢單筆記錄
	BookBean getBook(int bookID);
	
	List<String> getCategory();
	
	String getCategoryTag();
	
	List<BookBean> getPageBooks();

	int getPageNo();
	
	long getRecordCounts();

	int getRecordsPerPage();

	int getTotalPages();
	
	// 新增一筆記錄
	int saveBook(BookBean bean);

    void setPageNo(int pageNo);
	
    void setRecordsPerPage(int recordsPerPage);
	
    void setSelected(String selected);
	// 計算紀錄總筆數

	int updateBook(BookBean bean, long sizeInBytes) ;

}