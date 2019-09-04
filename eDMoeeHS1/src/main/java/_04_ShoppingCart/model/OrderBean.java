package _04_ShoppingCart.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// 本類別存放訂單資料
@Entity
@Table(name="Orders")
public class OrderBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderNo;
	private String memberId;
	private Double totalAmount;
	private String shippingAddress;
	private String bno;
	private String invoiceTitle;
	private Date orderDate;
	private Date shippingDate;
	private String cancelTag;
	@OneToMany(mappedBy="orderBean", cascade=CascadeType.ALL)
	private Set<OrderItemBean> items = new LinkedHashSet<>();

	public OrderBean() {

	}

	public OrderBean(Integer no, String memberId, Double totalAmount, String shippingAddress, String bNO,
			String invoiceTitle, Date orderDate, Date shippingDate, Set<OrderItemBean> items) {
		super();
		this.orderNo = no;
		this.memberId = memberId;
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.bno = bNO;
		this.invoiceTitle = invoiceTitle;
		this.orderDate = orderDate;
		this.shippingDate = shippingDate;
		this.items = items;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	// 說明多方表格的orderBean欄位為外鍵欄位，對照的主鍵為一方表格的orderno欄
	public Set<OrderItemBean> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemBean> items) {
		this.items = items;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getCancelTag() {
		return cancelTag;
	}

	public void setCancelTag(String cancelTag) {
		this.cancelTag = cancelTag;
	}

}
