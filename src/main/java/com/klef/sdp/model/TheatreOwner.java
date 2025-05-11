package com.klef.sdp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "theatreowner_table")
public class TheatreOwner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "owner_id")
	private int id;
	
	@Column(name = "owner_name", length = 50, nullable = false)
	private String name;
	
	@Column(name = "owner_email", length = 50, nullable = false)
	private String email;
	
	@Column(name = "owner_uname", length = 50, nullable = false)
	private String username;
	
	@Column(name = "owner_pwd", length = 50, nullable = false)
	private String password;
	
	@Column(name = "owner_mobileno", length = 20, nullable = false)
	private String mobileno;
	
	@Column(name = "owner_company", length = 100)
	private String company;
	
	@Column(name = "owner_verified", nullable = false)
	private boolean verified;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMobileno() {
		return mobileno;
	}
	
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}