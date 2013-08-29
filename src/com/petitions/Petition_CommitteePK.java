package com.petitions;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Petition_Committee database table.
 * 
 */
public class Petition_CommitteePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer committee_Emp_ID;

	private Integer petition_Number;

	public Petition_CommitteePK() {
	}
	
	public Integer getCommittee_Emp_ID() {
		return committee_Emp_ID;
	}


	public void setCommittee_Emp_ID(Integer committee_Emp_ID) {
		this.committee_Emp_ID = committee_Emp_ID;
	}


	public Integer getPetition_Number() {
		return petition_Number;
	}


	public void setPetition_Number(Integer petition_Number) {
		this.petition_Number = petition_Number;
	}


}