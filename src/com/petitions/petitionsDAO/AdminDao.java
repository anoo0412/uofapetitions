package com.petitions.petitionsDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.petitions.Committee_Member;
import com.petitions.Delegate_Decision;
import com.petitions.Delegate_DecisionPK;
import com.petitions.Department;
import com.petitions.PetitionDecision;
import com.petitions.Petition_Committee;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.util.SQLConnection;

public class AdminDao {
	public List<Student> getStudentPetitions() throws SQLException {
		Connection connection = null;
		List<Student> students = new ArrayList<Student>();
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("Select * from student_petition sp,college c, petition_type p, "
							+ "students s where s.sid = sp.sid and c.college_id = s.college_id"
							+ " and p.petition_id = sp.Petition_ID"
							+ " and sp.petition_status <> 'void' ORDER BY petition_status DESC");

			ResultSet result = statement.executeQuery();

			StudentPetition studentPetition;
			List<StudentPetition> studentPetitions = null;
			Student student = null;
			while (result.next()) {
				studentPetitions = new ArrayList<StudentPetition>();
				studentPetition = new StudentPetition();
				student = new Student();
				student.setSid(result.getInt("sid"));
				student.setCollege_name(result.getString("college_name"));
				student.setCollege_id(result.getInt("college_id"));
				student.setStudent_address(result.getString("student_address"));
				student.setStudent_city(result.getString("student_city"));
				student.setStudent_state(result.getString("student_state"));
				student.setStudent_emailId(result.getString("Student_emailId"));
				student.setStudent_Fname(result.getString("student_fname"));
				student.setStudent_LName(result.getString("student_lname"));
				studentPetition.setPetition_Number(result
						.getInt("Petition_Number"));
				studentPetition.setOrig_Petition_Date(result
						.getDate("orig_petition_date"));
				studentPetition.setPetition_Details(result
						.getString("Petition_Details"));
				studentPetition.setPetitionHeader(result
						.getString("Petition_header"));
				studentPetition.setPetition_Status(result
						.getString("Petition_Status"));
				studentPetition.setSchool_Session(result
						.getString("School_Session"));
				studentPetition.setSession_Year(result
						.getString("Session_Year"));
				studentPetition.setPetitionType(result
						.getString("petition_name"));

				// ----------------Department ID--------------------------//
				Department Department_ID = new Department();
				Department_ID.setDepartment_ID(result.getInt("Dept_ID"));
				studentPetition.setDepartment(Department_ID);

				// ----------------Supporting Doc------------------------//
				PreparedStatement statement1 = connection
						.prepareStatement("Select * from petition_sup_docs where Student_Petition_ID = ?");
				statement1.setString(1, result.getString("Petition_Number"));
				ResultSet result1 = statement1.executeQuery();
				ArrayList<Petition_Sup_Doc> petition_Sup_Docs_List = new ArrayList<Petition_Sup_Doc>();
				while (result1.next()) {
					Petition_Sup_Doc doc = new Petition_Sup_Doc();
					doc.setDocument_ID(result1.getInt("Document_ID"));
					doc.setPetition_number(result.getInt("Petition_Number"));
					doc.setDocument_Attachment(result1
							.getObject("Document_Attachment"));
					doc.setDocument_name(result1.getString("Document_name"));
					doc.setDocument_type(result1.getString("Document_type"));
					petition_Sup_Docs_List.add(doc);
				}
				studentPetition.setPetitionSupDocs(petition_Sup_Docs_List);

				// -------Delegate Decision------------------------------//
				PreparedStatement statement2 = connection
						.prepareStatement("Select * from delegate_decision where Petition_Number = ?");
				statement2.setString(1, result.getString("Petition_Number"));
				ResultSet result2 = statement2.executeQuery();
				ArrayList<Delegate_Decision> Decision_List = new ArrayList<Delegate_Decision>();
				while (result2.next()) {
					Delegate_Decision decision = new Delegate_Decision();
					decision.setComment_Header(result2
							.getString("Comment_Header"));
					decision.setComment_Body(result2.getString("Comment_Body"));
					decision.setOriginalDate(result2.getDate("Original_Date"));
					decision.setUpdatedDate(result2.getDate("Update_Date"));
					decision.setDean_Recommendation(result2
							.getString("Dean_Recommendation"));

					Delegate_DecisionPK decisionPK = new Delegate_DecisionPK();
					decisionPK.setPetition_Number(result
							.getInt("petition_number"));
					decisionPK.setAssigned_Delegate_ID(result2
							.getInt("Assigned_Delegate_ID"));
					decision.setId(decisionPK);
					// decision.setId(result2.getInt(""));
					Decision_List.add(decision);
				}
				studentPetition.setDelegateDecisions(Decision_List);

				// --------------Petition
				// Committee----------------------------//
				PreparedStatement statement3 = connection
						.prepareStatement("Select * from petition_Committee pc, "
								+ "Committee_Members cm where Petition_Number = ?"
								+ " and cm.Committee_Emp_ID = pc.Committee_Emp_ID");
				statement3.setString(1, result.getString("Petition_Number"));
				ResultSet result3 = statement3.executeQuery();
				ArrayList<Petition_Committee> Member_List = new ArrayList<Petition_Committee>();
				while (result3.next()) {
					Petition_Committee member = new Petition_Committee();
					member.setOriginalDate(result3.getDate("Original_Com_Date"));
					member.setUpdatedDate(result3.getDate("Updated_Com_Date"));
					member.setMember_Decision(result3
							.getString("Member_Decision"));
					// member.setId(result3.getString(""));
					member.setFname(result3.getString("Member_FName"));
					member.setLname(result3.getString("Member_LName"));
					member.setCommPetitionHeader(result3
							.getString("comm_petition_header"));
					member.setCommPetitionBody(result3
							.getString("comm_petition_body"));
					Member_List.add(member);
				}
				studentPetition.setPetitionCommittees(Member_List);
				studentPetitions.add(studentPetition);
				student.setStudentPetitions(studentPetitions);
				students.add(student);
			}
			return students;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return null;
	}

	public void assignReviewers(Integer petition_number, List<Integer> reviewers)
			throws Exception {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());

			for (int i = 0; i < reviewers.size(); i++) {
				// int reviewer = Integer.parseInt(reviewers.get(i).toString());
				PreparedStatement checkStatusStmt = connection
						.prepareStatement("select * from Petition_Committee "
								+ "where Petition_Number = ?"
								+ " and Committee_Emp_ID = " + reviewers.get(i));
				checkStatusStmt.setInt(1, petition_number);
				ResultSet rs = checkStatusStmt.executeQuery();
				if (!rs.next()) {
					PreparedStatement statement = connection
							.prepareStatement("insert into Petition_Committee"
									+ "(Petition_Number,Committee_Emp_ID,"
									+ " Original_Com_Date)" + " values (?,"
									+ reviewers.get(i) + ",?)");
					statement.setInt(1, petition_number);
					statement.setDate(2, sqlDate);
					statement.executeUpdate();
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public void finalVerdict(PetitionDecision petitionDecision) {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement
					.executeQuery("select count(*) from petition_decision where petition_number = "
							+ petitionDecision.getPetition_number());
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			if (result.next()) {
				if (result.getInt(1) != 0) {
					PreparedStatement updateStatement = connection
							.prepareStatement("Update petition_decision set "
									+ " update_Decision_Date = ?, Petition_Decision = ?, Comment_Header = ?, Comment_Body = ?"
									+ " where petition_number = ?");
					updateStatement.setDate(1, sqlDate);
					updateStatement.setString(2,
							petitionDecision.getPetition_Decision());
					updateStatement.setString(3,
							petitionDecision.getComment_Header());
					updateStatement.setString(4,
							petitionDecision.getComment_Body());
					updateStatement.setInt(5,
							petitionDecision.getPetition_number());

					updateStatement.executeBatch();
				} else {
					PreparedStatement insertStatment = connection
							.prepareStatement("insert into Petition_decision(petition_number, original_Decision_Date,"
									+ "Petition_Decision,Comment_Header, Comment_Body) values (?,?,?,?,?)");
					insertStatment.setInt(1,
							petitionDecision.getPetition_number());
					insertStatment.setDate(2, sqlDate);
					insertStatment.setString(3,
							petitionDecision.getPetition_Decision());
					insertStatment.setString(4,
							petitionDecision.getComment_Header());
					insertStatment.setString(5,
							petitionDecision.getComment_Body());
					insertStatment.executeUpdate();
				}
			}

			PreparedStatement statement2 = connection
					.prepareStatement("update student_petition set petition_status='completed' where petition_number=?");
			statement2.setInt(1, petitionDecision.getPetition_number());
			statement2.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Committee_Member> getCommitteeMembers() throws SQLException {
		Connection connection = null;
		List<Committee_Member> commMembers = new ArrayList<Committee_Member>();
		Committee_Member commMember;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from Committee_Members");
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				commMember = new Committee_Member();
				commMember.setCommittee_Emp_ID(result
						.getInt("Committee_Emp_ID"));
				commMember.setMember_FName(result.getString("Member_FName"));
				commMember.setMember_LName(result.getString("Member_LName"));
				commMembers.add(commMember);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return commMembers;
	}

}
