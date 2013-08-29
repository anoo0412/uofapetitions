package com.petitions.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.petitions.Student;
import com.petitions.StudentPetition;




public class MySQLDemo {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/petitions", "root", "password");
			String url = "select * from students";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(url);
			List<Student>  students = new ArrayList<Student>();
			Student student;
			while(result.next()) {
				student = new Student();
				student.setSid(result.getInt("SID"));
				student.setStudent_address(result.getString("student_address"));
				students.add(student);
			}
			insertDemo(connection);
			deleteDemo(connection);
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void insertDemo(Connection connection) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Insert into students(SID,Student_Fname,Student_Lname)" +
					"values ('S006','Sumatheja','Dasararaju')");
			Integer i = prepareStatement.executeUpdate();
			
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteDemo(Connection connection) {
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("Delete from students where SID = ?");
			prepareStatement.setString(1, "S001");
			Integer i = prepareStatement.executeUpdate();
			
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNewPetiton(StudentPetition studentPetition) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/petitions", "root", "password");
			
			PreparedStatement preparedStatement = connection.prepareStatement("");
			//preparedStatement.setString(1, studentPetition.getPetition_ID());
			preparedStatement.setString(2, studentPetition.getDean_Approval());
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
