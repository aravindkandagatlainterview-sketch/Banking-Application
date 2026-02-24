package com.BankingApp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	@Column(unique = true, nullable = false, updatable = false)
	private String accountNumber;
	private String acholdername;
	private double acbalance;
	private String status="Active";
	private short pin;
	private String job_role;
	private long phonenumber;
	private String password;
	private int statuscount;
	private int deactivecount;
	private String pancard;
	private long deletedbyid;
	private long otp;
	private String email;
	private int civilScore;
	private String account_type;
	private int age;

	public AccountEntity(long id, String acholdername, double acbalance, String status, short pin, String job_role,
			long phonenumber, String password, int statuscount, int deactivecount, String pancard, long deletedbyid,
			String email,short civilScore,String account_type,int age,String accountNumber) {
		super();
		Id = id;
		this.acholdername = acholdername;
		this.acbalance = acbalance;
		this.status = status;
		this.pin = pin;
		this.job_role = job_role;
		this.phonenumber = phonenumber;
		this.password = password;
		this.statuscount = statuscount;
		this.deactivecount = deactivecount;
		this.pancard = pancard;
		this.deletedbyid = deletedbyid;
		this.email = email;
		this.civilScore=civilScore;
		this.account_type=account_type;
		this.age=age;
		this.accountNumber=accountNumber;
	}
	
	
	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public int getCivilScore() {
		return civilScore;
	}

	public void setCivilScore(int i) {
		this.civilScore = i;
	}

	public long getOtp() {
		return otp;
	}

	public void setOtp(long otp) {
		this.otp = otp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getDeletedbyid() {
		return deletedbyid;
	}

	public void setDeletedbyid(long deletedbyid) {
		this.deletedbyid = deletedbyid;
	}

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	public int getDeactivecount() {
		return deactivecount;
	}

	public void setDeactivecount(int deactivecount) {
		this.deactivecount = deactivecount;
	}

	public int getStatuscount() {
		return statuscount;
	}

	public void setStatuscount(int statuscount) {
		this.statuscount = statuscount;
	}

	public AccountEntity() {
		this.status="Active";
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getAcholdername() {
		return acholdername;
	}

	public void setAcholdername(String acholdername) {
		this.acholdername = acholdername;
	}

	public double getAcbalance() {
		return acbalance;
	}

	public void setAcbalance(double acbalance) {
		this.acbalance = acbalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public short getPin() {
		return pin;
	}

	public void setPin(short pin) {
		this.pin = pin;
	}

	public String getJob_role() {
		return job_role;
	}

	public void setJob_role(String job_role) {
		this.job_role = job_role;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
