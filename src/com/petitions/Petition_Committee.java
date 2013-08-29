package com.petitions;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the Petition_Committee database table.
 * 
 */
public class Petition_Committee implements Serializable {
	private static final long serialVersionUID = 1L;

	private Petition_CommitteePK id;

	private Date originalDate;
	
	private String fname;
	
	private String lname;
	
	private Date updatedDate;

	private String member_Decision;
	
	private String commPetitionHeader;
	
	private String commPetitionBody;

	private StudentPetition studentPetition;
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getCommPetitionHeader() {
		return commPetitionHeader;
	}

	public void setCommPetitionHeader(String commPetitionHeader) {
		this.commPetitionHeader = commPetitionHeader;
	}

	public String getCommPetitionBody() {
		return commPetitionBody;
	}

	public void setCommPetitionBody(String commPetitionBody) {
		this.commPetitionBody = commPetitionBody;
	}

	public Petition_Committee() {
	}

	public Petition_CommitteePK getId() {
		return this.id;
	}

	public void setId(Petition_CommitteePK id) {
		this.id = id;
	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getMember_Decision() {
		return this.member_Decision;
	}

	public void setMember_Decision(String member_Decision) {
		this.member_Decision = member_Decision;
	}

	public StudentPetition getStudentPetition() {
		return this.studentPetition;
	}

	public void setStudentPetition(StudentPetition studentPetition) {
		this.studentPetition = studentPetition;
	}

}