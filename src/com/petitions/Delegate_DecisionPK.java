package com.petitions;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Delegate_Decision database table.
 * 
 */
public class Delegate_DecisionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer assigned_Delegate_ID;

	private Integer petition_Number;

	public Delegate_DecisionPK() {
	}
	
	public Integer getAssigned_Delegate_ID() {
		return assigned_Delegate_ID;
	}

	public void setAssigned_Delegate_ID(Integer assigned_Delegate_ID) {
		this.assigned_Delegate_ID = assigned_Delegate_ID;
	}

	public Integer getPetition_Number() {
		return petition_Number;
	}

	public void setPetition_Number(Integer petition_Number) {
		this.petition_Number = petition_Number;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Delegate_DecisionPK)) {
			return false;
		}
		Delegate_DecisionPK castOther = (Delegate_DecisionPK)other;
		return 
			this.assigned_Delegate_ID.equals(castOther.assigned_Delegate_ID)
			&& this.petition_Number.equals(castOther.petition_Number);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.assigned_Delegate_ID.hashCode();
		hash = hash * prime + this.petition_Number.hashCode();
		
		return hash;
	}
}