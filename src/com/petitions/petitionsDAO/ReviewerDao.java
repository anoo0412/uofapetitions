package com.petitions.petitionsDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.petitions.Delegate_Decision;
import com.petitions.Delegate_DecisionPK;
import com.petitions.Department;
import com.petitions.Petition_Committee;
import com.petitions.Petition_CommitteePK;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.util.SQLConnection;

public class ReviewerDao {
	public List<Student> getStudentPetitions(Integer committee_member_Id) throws SQLException {
		Connection connection = null;
		StudentPetition studentPetition;
		List<StudentPetition> studentPetitions = null;
		List<Student> students = new ArrayList<Student>();
		Student student = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement("Select * from students s," +
					" student_petition sp, petition_committee pc, college c, petition_type pt " +
					" where pc.Petition_Number = sp.Petition_Number" +
					" AND s.sid = sp.sid AND pc.committee_emp_id = ?" +
					" and sp.petition_id = pt.petition_id" +
					" and c.college_id = s.college_id ORDER BY petition_status DESC");
			statement.setInt(1, committee_member_Id);
						
			ResultSet result = statement.executeQuery();
						
			
			
			while(result.next()) {
				student = new Student();
				studentPetition = new StudentPetition();
				studentPetitions = new ArrayList<StudentPetition>();
				student.setSid(result.getInt("sid"));
				student.setCollege_name(result.getString("college_name"));
				student.setCollege_id(result.getInt("college_id"));
				student.setStudent_address(result.getString("student_address"));
				student.setStudent_city(result.getString("student_city"));
				student.setStudent_state(result.getString("student_state"));
				student.setStudent_emailId(result.getString("Student_emailId"));
				student.setStudent_Fname(result.getString("student_fname"));
				student.setStudent_LName(result.getString("student_lname"));
				studentPetition.setPetition_Number(result.getInt("Petition_Number"));
				studentPetition.setOrig_Petition_Date(result.getDate("orig_petition_date"));
				studentPetition.setPetition_Details(result.getString("Petition_Details"));
				studentPetition.setPetitionHeader(result.getString("Petition_Header"));
				studentPetition.setPetition_Status(result.getString("Petition_Status"));
				studentPetition.setSchool_Session(result.getString("School_Session"));
				studentPetition.setSession_Year(result.getString("Session_Year"));
				studentPetition.setPetition_ID(result.getInt("petition_ID"));
				studentPetition.setPetitionType(result.getString("petition_name"));
				
				//----------------Department ID--------------------------//
				Department Department_ID = new Department();
				Department_ID.setDepartment_ID(result.getInt("Dept_ID"));
				studentPetition.setDepartment(Department_ID);
				
				
				//----------------Supporting Doc------------------------//				
				PreparedStatement statement1 = connection.prepareStatement("Select * from petition_sup_docs where Student_Petition_ID = ?");
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
				PreparedStatement statement2 = connection.prepareStatement("Select * from delegate_decision where Petition_Number = ?");
				statement2.setString(1, result.getString("Petition_Number"));
				ResultSet result2 = statement2.executeQuery();
				ArrayList<Delegate_Decision> Decision_List = new ArrayList<Delegate_Decision>();
				while(result2.next()) {
					Delegate_Decision decision = new Delegate_Decision();					
					decision.setComment_Header(result2.getString("Comment_Header"));
					decision.setComment_Body(result2.getString("Comment_Body"));
					decision.setOriginalDate(result2.getDate("Original_Date"));
					decision.setDean_Recommendation(result2.getString("Dean_Recommendation"));
					
					Delegate_DecisionPK decisionPK = new Delegate_DecisionPK();
					decisionPK.setAssigned_Delegate_ID(result2.getInt("Assigned_Delegate_ID"));
					decision.setId(decisionPK);
					//decision.setId(result2.getInt(""));
					Decision_List.add(decision);
				}
				studentPetition.setDelegateDecisions(Decision_List);
				
				//---------------Present Committee Member decision---------//
				PreparedStatement statement3 = connection.prepareStatement("Select * from petition_committee" +
						" where Petition_Number = ? and Committee_Emp_ID = ?");
				statement3.setString(1, result.getString("Petition_Number"));
				statement3.setInt(2, committee_member_Id);
				ResultSet result3 = statement3.executeQuery();
				List<Petition_Committee> petition_Committees = new ArrayList<Petition_Committee>();
				Petition_Committee petition_Committee = null;
				while(result3.next()) {
					petition_Committee = new Petition_Committee();
					Petition_CommitteePK id = new Petition_CommitteePK();
					id.setCommittee_Emp_ID(committee_member_Id);
					id.setPetition_Number(result3.getInt("petition_number"));
					petition_Committee.setId(id);
					petition_Committee.setCommPetitionHeader(result3.getString("comm_petition_header"));
					petition_Committee.setCommPetitionBody(result3.getString("comm_petition_body"));
					petition_Committee.setMember_Decision(result3.getString("Member_Decision"));
					petition_Committees.add(petition_Committee);
				}
				studentPetition.setPetitionCommittees(petition_Committees);
				
				studentPetitions.add(studentPetition);
				student.setStudentPetitions(studentPetitions);
				students.add(student);
				}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		
		return students;
	}
	
	public void checkReviewerDecisionExists(Petition_Committee reviewerDecision) throws Exception {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from" +
					" Petition_Committee where " +
					"Committee_Emp_ID = ? and Petition_Number = ?");
			preparedStatement.setInt(1, reviewerDecision.getId().getCommittee_Emp_ID());
			preparedStatement.setInt(2, reviewerDecision.getId().getPetition_Number());
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getInt(1) != 0) {
					updateReviewerDecision(reviewerDecision);
				} else {
				insertReviewerDecision(reviewerDecision);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		
	}
	
	private void updateReviewerDecision(Petition_Committee reviewerDecision) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("update petition_committee set" +
					" comm_petition_header = ?,  comm_petition_body = ?, Updated_Com_Date = ?," +
					"Member_Decision = ? where Committee_Emp_ID = ? and Petition_Number = ?");
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			
			
			preparedStatement.setString(1, reviewerDecision.getCommPetitionHeader());
			preparedStatement.setString(2, reviewerDecision.getCommPetitionBody());
			preparedStatement.setDate(3, sqlDate);
			preparedStatement.setString(4, reviewerDecision.getMember_Decision());
			preparedStatement.setInt(5, reviewerDecision.getId().getCommittee_Emp_ID());
			preparedStatement.setInt(6, reviewerDecision.getId().getPetition_Number());
			
			preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	private void insertReviewerDecision(Petition_Committee reviewerDecision) throws SQLException {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("insert into petition_committee " +
					"values (?,?,?,?,?,?,?)");
			
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			
			preparedStatement.setInt(1, reviewerDecision.getId().getCommittee_Emp_ID());
			preparedStatement.setInt(2, reviewerDecision.getId().getPetition_Number());
			preparedStatement.setString(3, reviewerDecision.getCommPetitionHeader());
			preparedStatement.setString(4, reviewerDecision.getCommPetitionBody());
			preparedStatement.setDate(5, sqlDate);
			preparedStatement.setDate(6, sqlDate);
			preparedStatement.setString(7, reviewerDecision.getMember_Decision());
			
			preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
}
