package _03_listBooks.model;

//本類別封裝單筆訂單資料
public class OrderItem {
	private String title;
	private String author;
	private String companyName;
	private Integer qty = 0;
	private Integer bookID = 0;
	private Double price = 0.0;
	private Double discount = 1.0;

	public OrderItem(int bookID, int qty, double price, double discount, String title, String author,
			String companyName) {
		this.bookID = bookID;
		this.qty = qty;
		this.price = price;
		this.discount = discount;
		this.title = title;
		this.author = author;
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public OrderItem() {
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}