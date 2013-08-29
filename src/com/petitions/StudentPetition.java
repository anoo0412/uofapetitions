package com.petitions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the student_petition database table.
 * 
 */
public class StudentPetition implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer petition_Number;

	private String dean_Approval;

	private String dean_Comments;

	public String getDean_Approval() {
		return dean_Approval;
	}

	public void setDean_Approval(String dean_Approval) {
		this.dean_Approval = dean_Approval;
	}

	public String getDean_Comments() {
		return dean_Comments;
	}

	public void setDean_Comments(String dean_Comments) {
		this.dean_Comments = dean_Comments;
	}

	private String petition_Details;

	private Integer petition_ID;
	
	private String petitionType;
	
	private String petitionHeader;
	
	private String petition_Status;

	private String school_Session;

	private String session_Year;

	private Integer SID;
	
	private Date orig_Petition_Date;
	
	private Date update_date;
	
	private String status;
	
	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public String getPetitionHeader() {
		return petitionHeader;
	}

	public void setPetitionHeader(String petitionHeader) {
		this.petitionHeader = petitionHeader;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getOrig_Petition_Date() {
		return orig_Petition_Date;
	}

	public void setOrig_Petition_Date(Date orig_Petition_Date) {
		this.orig_Petition_Date = orig_Petition_Date;
	}


	public Integer getSID() {
		return SID;
	}

	public void setSID(Integer sID) {
		SID = sID;
	}

	public void setPetition_ID(Integer petition_ID) {
		this.petition_ID = petition_ID;
	}

	private List<Delegate_Decision> delegateDecisions;

	private List<Petition_Committee> petitionCommittees;

	private List<Petition_Sup_Doc> petitionSupDocs;

	private Department department;
	

	public StudentPetition() {
	}
	
	
	public Integer getPetition_Number() {
		return petition_Number;
	}

	public void setPetition_Number(Integer petition_Number) {
		this.petition_Number = petition_Number;
	}

	public Integer getPetition_ID() {
		return petition_ID;
	}

	public String getPetition_Details() {
		return this.petition_Details;
	}

	public void setPetition_Details(String petition_Details) {
		this.petition_Details = petition_Details;
	}


	public String getPetition_Status() {
		return this.petition_Status;
	}

	public void setPetition_Status(String petition_Status) {
		this.petition_Status = petition_Status;
	}

	public String getSchool_Session() {
		return this.school_Session;
	}

	public void setSchool_Session(String school_Session) {
		this.school_Session = school_Session;
	}

	public String getSession_Year() {
		return this.session_Year;
	}

	public void setSession_Year(String session_Year) {
		this.session_Year = session_Year;
	}

	public List<Delegate_Decision> getDelegateDecisions() {
		return this.delegateDecisions;
	}

	public void setDelegateDecisions(List<Delegate_Decision> delegateDecisions) {
		this.delegateDecisions = delegateDecisions;
	}

	public List<Petition_Committee> getPetitionCommittees() {
		return this.petitionCommittees;
	}

	public void setPetitionCommittees(List<Petition_Committee> petitionCommittees) {
		this.petitionCommittees = petitionCommittees;
	}

	public List<Petition_Sup_Doc> getPetitionSupDocs() {
		return this.petitionSupDocs;
	}

	public void setPetitionSupDocs(List<Petition_Sup_Doc> petitionSupDocs) {
		this.petitionSupDocs = petitionSupDocs;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}