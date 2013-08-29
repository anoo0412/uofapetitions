package com.petitions;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the petition_decision database table.
 * 
 */
public class PetitionDecision implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer decision_ID;
	
	private Integer petition_number;

	private String comment_Body;

	private String comment_Header;

	private Date decision_Date;

	private String petition_Decision;

	public PetitionDecision() {
	}

	public Integer getDecision_ID() {
		return decision_ID;
	}

	public void setDecision_ID(Integer decision_ID) {
		this.decision_ID = decision_ID;
	}

	public Integer getPetition_number() {
		return petition_number;
	}

	public void setPetition_number(Integer petition_number) {
		this.petition_number = petition_number;
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

	public Date getDecision_Date() {
		return this.decision_Date;
	}

	public void setDecision_Date(Date decision_Date) {
		this.decision_Date = decision_Date;
	}

	public String getPetition_Decision() {
		return this.petition_Decision;
	}

	public void setPetition_Decision(String petition_Decision) {
		this.petition_Decision = petition_Decision;
	}

}