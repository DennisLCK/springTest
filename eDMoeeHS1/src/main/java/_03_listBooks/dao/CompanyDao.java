package _03_listBooks.dao;

import java.util.List;

import _03_listBooks.model.CompanyBean;

public interface CompanyDao {

	List<CompanyBean> getCompany() ;

	CompanyBean getCompanyById() ;

	int getId();
	
	int getSelected();

	String getSelectTag();

	String getTagName();
	
	void setId(int id);
	
	void setSelected(int selected);

	void setTagName(String tagName);
	
}