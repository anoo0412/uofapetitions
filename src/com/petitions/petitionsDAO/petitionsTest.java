package com.petitions.petitionsDAO;

import java.util.List;

import com.petitions.Student;

public class petitionsTest {
	public static void main(String[] args) {
		DeanDAO dean = new DeanDAO();
		try {
			List<Student> stude = dean.getStudentPetitions(1);
			System.out.println(stude);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
