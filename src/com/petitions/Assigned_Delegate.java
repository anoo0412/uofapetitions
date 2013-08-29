package com.petitions;

import java.io.Serializable;


/**
 * The persistent class for the assigned_Delegate database table.
 * 
 */
public class Assigned_Delegate implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer assigned_Delegate_ID;

	private String dean_ID_Number;

	private String delegate_Email;

	private String delegate_FName;

	private String delegate_LName;

	private String delegate_Phone;

	public Assigned_Delegate() {
	}

	public Integer getAssigned_Delegate_ID() {
		return assigned_Delegate_ID;
	}

	public void setAssigned_Delegate_ID(Integer assigned_Delegate_ID) {
		this.assigned_Delegate_ID = assigned_Delegate_ID;
	}

	public String getDean_ID_Number() {
		return this.dean_ID_Number;
	}

	public void setDean_ID_Number(String dean_ID_Number) {
		this.dean_ID_Number = dean_ID_Number;
	}

	public String getDelegate_Email() {
		return this.delegate_Email;
	}

	public void setDelegate_Email(String delegate_Email) {
		this.delegate_Email = delegate_Email;
	}

	public String getDelegate_FName() {
		return this.delegate_FName;
	}

	public void setDelegate_FName(String delegate_FName) {
		this.delegate_FName = delegate_FName;
	}

	public String getDelegate_LName() {
		return this.delegate_LName;
	}

	public void setDelegate_LName(String delegate_LName) {
		this.delegate_LName = delegate_LName;
	}

	public String getDelegate_Phone() {
		return this.delegate_Phone;
	}

	public void setDelegate_Phone(String delegate_Phone) {
		this.delegate_Phone = delegate_Phone;
	}

}