package com.petitions;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the College_Dean database table.
 * 
 */
public class College_Dean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dean_Emp_Id;

	private String college_ID;

	private String dean_Email;

	private String dean_FName;

	private String dean_LName;

	private String dean_Phone;

	public College_Dean() {
	}

	public String getDean_Emp_Id() {
		return this.dean_Emp_Id;
	}

	public void setDean_Emp_Id(String dean_Emp_Id) {
		this.dean_Emp_Id = dean_Emp_Id;
	}

	public String getCollege_ID() {
		return this.college_ID;
	}

	public void setCollege_ID(String college_ID) {
		this.college_ID = college_ID;
	}

	public String getDean_Email() {
		return this.dean_Email;
	}

	public void setDean_Email(String dean_Email) {
		this.dean_Email = dean_Email;
	}

	public String getDean_FName() {
		return this.dean_FName;
	}

	public void setDean_FName(String dean_FName) {
		this.dean_FName = dean_FName;
	}

	public String getDean_LName() {
		return this.dean_LName;
	}

	public void setDean_LName(String dean_LName) {
		this.dean_LName = dean_LName;
	}

	public String getDean_Phone() {
		return this.dean_Phone;
	}

	public void setDean_Phone(String dean_Phone) {
		this.dean_Phone = dean_Phone;
	}

}