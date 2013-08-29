package com.petitions.petitionsDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.petitions.College;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Petition_Type;
import com.petitions.SchoolSession;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.util.PetitionConstants;
import com.petitions.util.SQLConnection;

public class StudentDao {

	public Student getStudentPetitions(Integer SID) throws Exception {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("Select * from students where SID = ?");
			statement.setInt(1, SID);

			ResultSet resultSet = statement.executeQuery();
			Student student = new Student();
			while (resultSet.next()) {
				student.setStudent_Fname(resultSet.getString("student_Fname"));
				student.setStudent_LName(resultSet.getString("student_Lname"));
				student.setStudent_Major(resultSet.getString("student_major"));
				student.setStudent_emailId(resultSet
						.getString("student_emailid"));
				student.setStudent_phone(resultSet.getString("student_phone"));
				student.setCollege_id(resultSet.getInt("college_id"));
				student.setStudent_address(resultSet
						.getString("student_address"));
				student.setStudent_city(resultSet.getString("student_city"));
				student.setStudent_state(resultSet.getString("student_state"));
				student.setStudent_zip(resultSet.getString("student_zip"));
			}

			statement = connection
					.prepareStatement("Select * from student_petition where SID = ? ORDER BY petition_status DESC");
			statement.setInt(1, SID);

			ResultSet result = statement.executeQuery();

			StudentPetition studentPetition;
			List<StudentPetition> studentPetitions = new ArrayList<StudentPetition>();

			while (result.next()) {
				studentPetition = new StudentPetition();

				studentPetition.setPetition_Number(result
						.getInt("Petition_Number"));
				studentPetition.setOrig_Petition_Date(result
						.getDate("orig_petition_date"));
				studentPetition.setPetition_Details(result
						.getString("Petition_Details"));
				studentPetition.setPetitionHeader(result
						.getString("Petition_Header"));
				studentPetition.setPetition_Status(result
						.getString("Petition_Status"));
				studentPetition.setSchool_Session(result
						.getString("School_Session"));
				studentPetition.setPetition_ID(result.getInt("petition_id"));
				studentPetition.setSession_Year(result
						.getString("Session_Year"));
				studentPetition.setUpdate_date(result.getDate("Update_date"));

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
					 doc.setDocument_Attachment(result1.getObject("Document_Attachment"));
					petition_Sup_Docs_List.add(doc);
				}
				studentPetition.setPetitionSupDocs(petition_Sup_Docs_List);

				System.out.println(studentPetition.getPetition_Status());
				studentPetitions.add(studentPetition);

			}
			student.setStudentPetitions(studentPetitions);
			return student;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer checkStudentExistence(Student student) throws Exception {
		Connection connection = null;
		Integer id = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("Select * from students where SID = ?");
			statement.setInt(1, student.getSid());

			ResultSet resultSet = statement.executeQuery();
			id = null;
			if (resultSet.next()) {
				updateStudent(student);
				id = student.getSid();
			} else {
				id = insertStudent(student);
			}

		} catch (Exception e) {
			throw new Exception();
		}
		return id;
	}

	public void updateStudentPetition(StudentPetition studentPetition) {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("update student_petition set "
							+ "petition_header = ?, Petition_Details = ? , "
							+ "School_Session = ?, Session_Year = ?,"
							+ " update_date = ? where Petition_Number = ?");
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			preparedStatement.setString(1, studentPetition.getPetitionHeader());
			preparedStatement.setString(2,
					studentPetition.getPetition_Details());
			preparedStatement.setString(3, studentPetition.getSchool_Session());
			preparedStatement.setString(4, studentPetition.getSession_Year());
			preparedStatement.setDate(5, sqlDate);
			preparedStatement.setInt(6, studentPetition.getPetition_Number());

			preparedStatement.executeUpdate();
			for (Petition_Sup_Doc doc : studentPetition.getPetitionSupDocs()) {
				doc.setPetition_number(studentPetition.getPetition_Number());
			}
			insertDocuments(studentPetition.getPetitionSupDocs());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer updateStudent(Student student) throws Exception {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("update students set Student_Fname = ?, "
							+ "Student_LName =?, Student_Major= ?,Student_emailId =?,"
							+ " Student_phone = ?, college_id =?,"
							+ " Student_address = ?,Student_city =?,"
							+ " Student_state = ?, Student_zip = ? where sid = ?");

			preparedStatement.setString(1, student.getStudent_Fname());
			preparedStatement.setString(2, student.getStudent_LName());
			preparedStatement.setString(3, student.getStudent_Major());
			preparedStatement.setString(4, student.getStudent_emailId());
			preparedStatement.setString(5, student.getStudent_phone());
			preparedStatement.setInt(6, student.getCollege_id());
			preparedStatement.setString(7, student.getStudent_address());
			preparedStatement.setString(8, student.getStudent_city());
			preparedStatement.setString(9, student.getStudent_state());
			preparedStatement.setString(10, student.getStudent_zip());
			preparedStatement.setInt(11, student.getSid());

			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public Integer insertStudent(Student student) throws Exception {
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement("Insert into students(SID,Student_Fname, Student_LName,"
							+ " Student_Major, Student_emailId, Student_phone, Student_college, "
							+ "Student_address, Student_city, Student_state,"
							+ " Student_zip) values" + " (?,?,?,?,?,?,?,?,?,?)");

			preparedStatement.setInt(1, student.getSid());
			preparedStatement.setString(2, student.getStudent_Fname());
			preparedStatement.setString(3, student.getStudent_LName());
			preparedStatement.setString(4, student.getStudent_Major());
			preparedStatement.setString(5, student.getStudent_emailId());
			preparedStatement.setString(6, student.getStudent_phone());
			preparedStatement.setInt(7, student.getCollege_id());
			preparedStatement.setString(8, student.getStudent_address());
			preparedStatement.setString(9, student.getStudent_city());
			preparedStatement.setString(10, student.getStudent_state());
			preparedStatement.setString(11, student.getStudent_zip());

			preparedStatement.executeUpdate();
			ResultSet newResult = preparedStatement.getGeneratedKeys();
			Integer newId = null;
			while (newResult.next()) {
				newId = newResult.getInt(1);
			}

			return newId;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertStudentPetition(StudentPetition studentPetition)
			throws Exception {
		Connection connection = null;
		Integer id = null;
		try {
			connection = SQLConnection.getConnection();

			PreparedStatement preparedStatement = connection
					.prepareStatement(
							"Insert into student_petition(Orig_Petition_Date,"
									+ " Petition_Details, Petition_Status,"
									+ " petition_header, School_Session, Session_Year, Petition_ID, SID) "
									+ "values (?,?,?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setString(2,
					studentPetition.getPetition_Details());
			preparedStatement.setString(3, PetitionConstants.OPEN);
			preparedStatement.setString(4, studentPetition.getPetitionHeader());
			preparedStatement.setString(5, studentPetition.getSchool_Session());
			preparedStatement.setString(6, studentPetition.getSession_Year());
			preparedStatement.setInt(7, studentPetition.getPetition_ID());
			preparedStatement.setInt(8, studentPetition.getSID());

			preparedStatement.executeUpdate();

			ResultSet generated_id = preparedStatement.getGeneratedKeys();

			if (generated_id.next()) {
				id = generated_id.getInt(1);
			}

			for (Petition_Sup_Doc doc : studentPetition.getPetitionSupDocs()) {
				doc.setPetition_number(id);
			}

			insertDocuments(studentPetition.getPetitionSupDocs());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			connection.close();
		}
	}

	/**
	 * This method is for inserting documents into the database
	 * @param docs
	 * @throws SQLException
	 */
	public void insertDocuments(List<Petition_Sup_Doc> docs)
			throws SQLException {
 		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("insert into "
							+ "Petition_Sup_Docs(Student_Petition_ID,Document_Name,document_type,Document_Attachment)"
							+ " values (?,?,?,?)");

			for (Petition_Sup_Doc doc : docs) {
				statement.setInt(1, doc.getPetition_number());
				UploadedFile file = (UploadedFile)doc.getDocument_Attachment();
				statement.setObject(2, file.getFileName());
				statement.setObject(3, file.getContentType());
				statement.setObject(4, file.getInputstream());
				statement.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public List<Petition_Type> getRequestTypes() throws Exception {
		Connection connection = null;
		List<Petition_Type> petitionTypes = new ArrayList<Petition_Type>();
		try {
			connection = SQLConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select petition_id, petition_name from Petition_Type");

			Petition_Type pet = null;
			while (resultSet.next()) {
				pet = new Petition_Type();
				pet.setPetition_ID(resultSet.getInt("petition_id"));
				pet.setPetition_Name(resultSet.getString("petition_name"));
				petitionTypes.add(pet);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			connection.close();
		}
		return petitionTypes;
	}

	public List<College> getColleges() throws Exception {
		Connection connection = null;
		List<College> colleges = new ArrayList<College>();
		try {
			connection = SQLConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select college_id,college_name from college");

			College col = null;
			while (resultSet.next()) {
				col = new College();
				col.setCollege_ID(resultSet.getString("college_Id"));
				col.setCollege_Name(resultSet.getString("college_name"));
				colleges.add(col);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			connection.close();
		}
		return colleges;
	}

	public List<SchoolSession> getSchoolSessions() throws Exception {
		Connection connection = null;
		List<SchoolSession> schoolSession = new ArrayList<SchoolSession>();
		try {
			connection = SQLConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select session_id,school_session from school_session;");

			SchoolSession pet = null;
			while (resultSet.next()) {
				pet = new SchoolSession();
				pet.setSessionId(resultSet.getInt(1));
				pet.setSchoolSession(resultSet.getString(2));
				schoolSession.add(pet);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			connection.close();
		}
		return schoolSession;
	}

	public List<String> getStates() throws Exception {
		Connection connection = null;
		List<String> states = new ArrayList<String>();
		try {
			connection = SQLConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select state_name from tbl_state");

			while (resultSet.next()) {
				states.add(resultSet.getString("state_name"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			connection.close();
		}
		return states;
	}
}
