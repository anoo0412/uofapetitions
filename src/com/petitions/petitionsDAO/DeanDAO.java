package com.petitions.petitionsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.petitions.Delegate_Decision;
import com.petitions.Delegate_DecisionPK;
import com.petitions.Department;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.util.SQLConnection;

public class DeanDAO {
	
	public void checkStudentPetitionStatus(Delegate_Decision delegate_Decision)
	{
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("select count(*) from delegate_decision " +
					"where assigned_delegate_Id = ?" +
					" and petition_number = ?");
			statement.setInt(1, delegate_Decision.getId().getAssigned_Delegate_ID());
			statement.setInt(2, delegate_Decision.getId().getPetition_Number());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				if(result.getInt(1) == 0) {
					insertStudentPetition(delegate_Decision);
				} else {
					updateStudentPetitionDecision(delegate_Decision);
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	private void insertStudentPetition(Delegate_Decision delegate_Decision) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement
			("insert into delegate_decision(assigned_delegate_Id, petition_number, comment_header, comment_body," +
					" dean_recommendation, original_date) values (?,?,?,?,?,?);");
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			preparedStatement.setInt(1, delegate_Decision.getId().getAssigned_Delegate_ID());
			preparedStatement.setInt(2, delegate_Decision.getId().getPetition_Number());
			preparedStatement.setString(3, delegate_Decision.getComment_Header());
			preparedStatement.setString(4, delegate_Decision.getComment_Body());
			preparedStatement.setString(5, delegate_Decision.getDean_Recommendation());
			preparedStatement.setDate(6, sqlDate);
			
			preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	
	public List<Student> getStudentPetitions(Integer delegate_Id) throws Exception {
		Connection connection = null;
		try {
			
			connection = SQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("select *  from students s" +
					",student_petition sp, assigned_delegate ad, " +
					"college c,petition_type p where s.sid = sp.sid and" +
					" s.college_id = ad.college_id and c.college_id = s.college_id " +
					"and ad.Assigned_Delegate_ID = ? and sp.petition_Id = p.petition_Id " +
					"and petition_status = 'open'");
			statement.setInt(1, delegate_Id);
						
			ResultSet result = statement.executeQuery();
						
			StudentPetition studentPetition;
			List<StudentPetition> studentPetitions = new ArrayList<StudentPetition>();
			List<Student> students = new ArrayList<Student>();
			while(result.next()) {
				studentPetitions = new ArrayList<StudentPetition>();
				studentPetition = new StudentPetition();
				Student student = new Student();
				student.setSid(result.getInt("sid"));
				student.setCollege_name(result.getString("college_name"));
				student.setCollege_id(result.getInt("college_id"));
				student.setStudent_address(result.getString("student_address"));
				student.setStudent_city(result.getString("student_city"));
				student.setStudent_state(result.getString("student_state"));
				student.setStudent_emailId(result.getString("Student_emailId"));
				student.setStudent_Fname(result.getString("student_fname"));
				student.setStudent_Major(result.getString("Student_Major"));
				student.setStudent_LName(result.getString("student_lname"));
				studentPetition.setPetition_Number(result.getInt("Petition_Number"));
				studentPetition.setOrig_Petition_Date(result.getDate("orig_petition_date"));
				studentPetition.setPetitionHeader(result.getString("petition_header"));
				studentPetition.setPetition_Details(result.getString("Petition_Details"));
				studentPetition.setPetition_Status(result.getString("Petition_Status"));
				studentPetition.setSchool_Session(result.getString("School_Session"));
				studentPetition.setSession_Year(result.getString("Session_Year"));
				studentPetition.setPetition_ID(result.getInt("petition_ID"));
				studentPetition.setPetition_Status(result.getString("petition_Status"));
				studentPetition.setPetitionType(result.getString("Petition_Name"));
				
				//----------------Department ID--------------------------//
				Department Department_ID = new Department();
				Department_ID.setDepartment_ID(result.getInt("Dept_ID"));
				studentPetition.setDepartment(Department_ID);
				
				
				//----------------Supporting Doc------------------------//				
				PreparedStatement statement1 = connection.prepareStatement("Select * from petition_sup_docs where " +
						"Student_Petition_ID = ?");
				statement1.setString(1, result.getString("Petition_Number"));
				ResultSet result1 = statement1.executeQuery();
				ArrayList<Petition_Sup_Doc> petition_Sup_Docs_List = new ArrayList<Petition_Sup_Doc>();
				while(result1.next()) {
					Petition_Sup_Doc doc = new Petition_Sup_Doc();					
					doc.setDocument_ID(result1.getInt("Document_ID"));
					doc.setPetition_number(result.getInt("Petition_Number"));
					doc.setDocument_Attachment(result1.getObject("Document_Attachment"));
					doc.setDocument_name(result1.getString("Document_name"));
					doc.setDocument_type(result1.getString("Document_type"));
					petition_Sup_Docs_List.add(doc);
				}
				studentPetition.setPetitionSupDocs(petition_Sup_Docs_List);
				
				//-------Delegate Decision------------------------------//
				PreparedStatement statement2 = connection.prepareStatement("Select * from delegate_decision where" +
						" Petition_Number = ?");
				statement2.setString(1, result.getString("Petition_Number"));
				ResultSet result2 = statement2.executeQuery();
				ArrayList<Delegate_Decision> Decision_List = new ArrayList<Delegate_Decision>();
				while(result2.next()) {
					Delegate_Decision decision = new Delegate_Decision();					
					decision.setComment_Header(result2.getString("Comment_Header"));
					decision.setComment_Body(result2.getString("Comment_Body"));
					decision.setOriginalDate(result2.getDate("original_Date"));
					decision.setDean_Recommendation(result2.getString("Dean_Recommendation"));
					
					Delegate_DecisionPK decisionPK = new Delegate_DecisionPK();
					decisionPK.setAssigned_Delegate_ID(result.getInt("Assigned_Delegate_ID"));
					decision.setId(decisionPK);
					//decision.setId(result2.getInt(""));
					Decision_List.add(decision);
				}
				studentPetition.setDelegateDecisions(Decision_List);
				
				studentPetitions.add(studentPetition);
				student.setStudentPetitions(studentPetitions);		
				students.add(student);
				}
			return students;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	private void updateStudentPetitionDecision(Delegate_Decision delegate_Decision) {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement
			("update delegate_decision set comment_header = ?, " +
					"comment_body = ?, " +
					"dean_recommendation = ?, original_date = ? " +
					"where assigned_delegate_id = ? and petition_number = ?");
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			
			preparedStatement.setString(1, delegate_Decision.getComment_Header());
			preparedStatement.setString(2, delegate_Decision.getComment_Body());
			preparedStatement.setString(3, delegate_Decision.getDean_Recommendation());
			preparedStatement.setDate(4, sqlDate);
			preparedStatement.setInt(5, delegate_Decision.getId().getAssigned_Delegate_ID());
			preparedStatement.setInt(6, delegate_Decision.getId().getPetition_Number());
			
			preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
