package _01_register.dao;

import java.sql.Connection;
import java.util.List;

import _01_register.model.MemberBean;
import _04_ShoppingCart.model.OrderBean;

public interface MemberDao {
	
	MemberBean checkIDPassword(String userId, String password);
	
	boolean idExists(String id);

	MemberBean queryMember(String id);
	
	int saveMember(MemberBean mb);
	
	void setConnection(Connection con);
	
	void updateUnpaidOrderAmount(OrderBean ob);
	
	List<MemberBean> getAllMembers();

}