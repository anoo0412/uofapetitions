package com.petitions;

import java.io.Serializable;


/**
 * The persistent class for the Login_Fields database table.
 * 
 */
public class Login_Field implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer netId;
	
	private String loginName;

	private String loginPassword;

	private String role;

	public Login_Field() {
	}
	
	public Integer getNetId() {
		return netId;
	}

	public void setNetId(Integer netId) {
		this.netId = netId;
	}



	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}