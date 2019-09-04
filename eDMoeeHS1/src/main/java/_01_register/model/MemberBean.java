package _01_register.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Member")
public class MemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seqNo")
	private Integer pKey;
	private String memberId;
	private String name;
	private String password;
	private String address;
	private String email;
	private String tel;
	private String userType;
	private Timestamp registerTime;
	private Double totalAmt;
	private Blob memberImage;
	private Clob comment;
	private String fileName;
	private Double unpaid_amount;

	public MemberBean(Integer pKey, String memberId, String name, String password, String address, String email,
			String tel, String userType, Timestamp registerTime, Double totalAmt, Double unpaid_amount,
			Blob memberImage, String fileName) {
		this.pKey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.userType = userType;
		this.registerTime = registerTime;
		this.totalAmt = totalAmt;
		this.unpaid_amount = unpaid_amount;
		this.fileName = fileName;
		this.memberImage = memberImage;
	}
	public MemberBean(Integer pKey, String memberId, String name, String address, String email,
			String tel) {
		this.pKey = pKey;
		this.memberId = memberId;
		
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		
		
	}
	public MemberBean() {
	}

	public Integer getPKey() {
		return pKey;
	}

	public void setPKey(int pKey) {
		this.pKey = pKey;
	}

	public Blob getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(Blob memberImage) {
		this.memberImage = memberImage;
	}

	public Clob getComment() {
		return comment;
	}

	public void setComment(Clob comment) {
		this.comment = comment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setTs(Timestamp ts) {
		this.registerTime = ts;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		email = mail;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String toString() {
		return "userid=" + memberId + "   password=" + password;
	}

	public String getUserType() {
		return userType;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public Double getUnpaid_amount() {
		return unpaid_amount;
	}

	public void setUnpaid_amount(Double unpaid_amount) {
		this.unpaid_amount = unpaid_amount;
	}
}
