package com.petitions;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Petition_Type database table.
 * 
 */
public class Petition_Type implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer petition_ID;

	private String petition_Description;

	private String petition_Name;

	public Petition_Type() {
	}

	

	public Integer getPetition_ID() {
		return petition_ID;
	}



	public void setPetition_ID(Integer petition_ID) {
		this.petition_ID = petition_ID;
	}



	public String getPetition_Description() {
		return this.petition_Description;
	}

	public void setPetition_Description(String petition_Description) {
		this.petition_Description = petition_Description;
	}

	public String getPetition_Name() {
		return this.petition_Name;
	}

	public void setPetition_Name(String petition_Name) {
		this.petition_Name = petition_Name;
	}

}