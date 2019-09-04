package _04_ShoppingCart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="OrderItems")
public class OrderItemBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seqno;
	private Integer orderNo;
	private Integer bookId;
	private String description;
	private Integer quantity;
	private Double unitPrice;
	private Double discount;
	@ManyToOne
	@JoinColumn(name = "FK_OrderBean_orderNo")
    private OrderBean orderBean;
	public OrderItemBean(Integer seqno, Integer orderNo, Integer bookID, String description, Integer quantity,
			Double unitPrice, Double discount) {
		super();
		this.seqno = seqno;
		this.orderNo = orderNo;
		this.bookId = bookID;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}

	public OrderItemBean(Integer orderNo, Integer bookID, String description, Integer quantity, Double unitPrice,
			Double discount) {
		super();

		this.orderNo = orderNo;
		this.bookId = bookID;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}

	public OrderItemBean() {

	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookID) {
		this.bookId = bookID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}
}