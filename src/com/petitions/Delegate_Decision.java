package com.petitions;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Delegate_Decision database table.
 * 
 */
public class Delegate_Decision implements Serializable {
	private static final long serialVersionUID = 1L;

	private Delegate_DecisionPK id;

	private String comment_Body;

	private String comment_Header;

	private Date originalDate;
	
	private Date updatedDate;
	
	

	private String dean_Recommendation;

	//bi-directional many-to-one association to StudentPetition
	private StudentPetition studentPetition;

	public Delegate_Decision() {
	}

	public Delegate_DecisionPK getId() {
		return this.id;
	}

	public void setId(Delegate_DecisionPK id) {
		this.id = id;
	}

	public String getComment_Body() {
		return this.comment_Body;
	}

	public void setComment_Body(String comment_Body) {
		this.comment_Body = comment_Body;
	}

	public String getComment_Header() {
		return this.comment_Header;
	}

	public void setComment_Header(String comment_Header) {
		this.comment_Header = comment_Header;
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

	public String getDean_Recommendation() {
		return this.dean_Recommendation;
	}

	public void setDean_Recommendation(String dean_Recommendation) {
		this.dean_Recommendation = dean_Recommendation;
	}

	public StudentPetition getStudentPetition() {
		return this.studentPetition;
	}

	public void setStudentPetition(StudentPetition studentPetition) {
		this.studentPetition = studentPetition;
	}

}