package com.petitions;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the Students database table.
 * 
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer sid;

	private String student_address;

	private String student_city;

	private Integer college_id;
	
	private String college_name;

	private String student_emailId;

	private String student_Fname;

	private String student_LName;

	private String student_Major;

	private String student_phone;

	private String student_state;

	private String student_zip;

	private List<StudentPetition> studentPetitions;

	public List<StudentPetition> getStudentPetitions() {
		return studentPetitions;
	}
	
	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}



	public void setStudentPetitions(List<StudentPetition> studentPetitions) {
		this.studentPetitions = studentPetitions;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getStudent_address() {
		return this.student_address;
	}

	public void setStudent_address(String student_address) {
		this.student_address = student_address;
	}

	public String getStudent_city() {
		return this.student_city;
	}

	public void setStudent_city(String student_city) {
		this.student_city = student_city;
	}

	public Integer getCollege_id() {
		return college_id;
	}

	public void setCollege_id(Integer college_id) {
		this.college_id = college_id;
	}

	public String getStudent_emailId() {
		return this.student_emailId;
	}

	public void setStudent_emailId(String student_emailId) {
		this.student_emailId = student_emailId;
	}

	public String getStudent_Fname() {
		return this.student_Fname;
	}

	public void setStudent_Fname(String student_Fname) {
		this.student_Fname = student_Fname;
	}

	public String getStudent_LName() {
		return this.student_LName;
	}

	public void setStudent_LName(String student_LName) {
		this.student_LName = student_LName;
	}

	public String getStudent_Major() {
		return this.student_Major;
	}

	public void setStudent_Major(String student_Major) {
		this.student_Major = student_Major;
	}

	public String getStudent_phone() {
		return this.student_phone;
	}

	public void setStudent_phone(String student_phone) {
		this.student_phone = student_phone;
	}

	public String getStudent_state() {
		return this.student_state;
	}

	public void setStudent_state(String student_state) {
		this.student_state = student_state;
	}

	public String getStudent_zip() {
		return this.student_zip;
	}

	public void setStudent_zip(String student_zip) {
		this.student_zip = student_zip;
	}

}