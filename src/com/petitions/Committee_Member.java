package com.petitions;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the Committee_Members database table.
 * 
 */
public class Committee_Member implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer committee_Emp_ID;

	private Date end_Date;

	private String member_FName;

	private String member_LName;

	private String member_Title;

	private Date start_Date;

	public Committee_Member() {
	}

	
	public Integer getCommittee_Emp_ID() {
		return committee_Emp_ID;
	}

	public void setCommittee_Emp_ID(Integer committee_Emp_ID) {
		this.committee_Emp_ID = committee_Emp_ID;
	}



	public Date getEnd_Date() {
		return this.end_Date;
	}

	public void setEnd_Date(Date end_Date) {
		this.end_Date = end_Date;
	}

	public String getMember_FName() {
		return this.member_FName;
	}

	public void setMember_FName(String member_FName) {
		this.member_FName = member_FName;
	}

	public String getMember_LName() {
		return this.member_LName;
	}

	public void setMember_LName(String member_LName) {
		this.member_LName = member_LName;
	}

	public String getMember_Title() {
		return this.member_Title;
	}

	public void setMember_Title(String member_Title) {
		this.member_Title = member_Title;
	}

	public Date getStart_Date() {
		return this.start_Date;
	}

	public void setStart_Date(Date start_Date) {
		this.start_Date = start_Date;
	}

}