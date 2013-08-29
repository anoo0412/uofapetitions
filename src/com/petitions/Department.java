package com.petitions;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the department database table.
 * 
 */
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer department_ID;

	private String college_ID;

	private int dept_building_Number;

	private String dept_Name;

	private String dept_Street;

	private List<StudentPetition> studentPetitions;

	public Department() {
	}

	public Integer getDepartment_ID() {
		return department_ID;
	}



	public void setDepartment_ID(Integer department_ID) {
		this.department_ID = department_ID;
	}



	public String getCollege_ID() {
		return this.college_ID;
	}

	public void setCollege_ID(String college_ID) {
		this.college_ID = college_ID;
	}

	public int getDept_building_Number() {
		return this.dept_building_Number;
	}

	public void setDept_building_Number(int dept_building_Number) {
		this.dept_building_Number = dept_building_Number;
	}

	public String getDept_Name() {
		return this.dept_Name;
	}

	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}

	public String getDept_Street() {
		return this.dept_Street;
	}

	public void setDept_Street(String dept_Street) {
		this.dept_Street = dept_Street;
	}

	public List<StudentPetition> getStudentPetitions() {
		return this.studentPetitions;
	}

	public void setStudentPetitions(List<StudentPetition> studentPetitions) {
		this.studentPetitions = studentPetitions;
	}

}