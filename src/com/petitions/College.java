package com.petitions;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the College database table.
 * 
 */
public class College implements Serializable {
	private static final long serialVersionUID = 1L;

	private String college_ID;

	private int building_No;

	private String building_Phone;

	private String building_Street;

	private String college_Name;

	public College() {
	}

	public String getCollege_ID() {
		return this.college_ID;
	}

	public void setCollege_ID(String college_ID) {
		this.college_ID = college_ID;
	}

	public int getBuilding_No() {
		return this.building_No;
	}

	public void setBuilding_No(int building_No) {
		this.building_No = building_No;
	}

	public String getBuilding_Phone() {
		return this.building_Phone;
	}

	public void setBuilding_Phone(String building_Phone) {
		this.building_Phone = building_Phone;
	}

	public String getBuilding_Street() {
		return this.building_Street;
	}

	public void setBuilding_Street(String building_Street) {
		this.building_Street = building_Street;
	}

	public String getCollege_Name() {
		return this.college_Name;
	}

	public void setCollege_Name(String college_Name) {
		this.college_Name = college_Name;
	}

}