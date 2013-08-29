package com.petitions.managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.petitions.College;
import com.petitions.DummyLogin;
import com.petitions.Login_Field;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Petition_Type;
import com.petitions.SchoolSession;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.petitionsDAO.StudentDao;

@ManagedBean(name = "student")
@SessionScoped
public class StudentBean {
	private List<Petition_Type> requestTypes = new ArrayList<Petition_Type>();
	private List<SchoolSession> semester = new ArrayList<SchoolSession>();
	private List<College> colleges = new ArrayList<College>();
	private StudentPetition studentPetition = new StudentPetition();
	private Student student1 = new Student();
	private List<StudentPetition> studentPetitions = new ArrayList<StudentPetition>();
	private StudentPetition editedStudentPetition;
	private Student checkStatusStudent;
	private List<String> states;
	private List<Petition_Sup_Doc> docs = new ArrayList<Petition_Sup_Doc>();
	private String fileName;

	public List<Petition_Sup_Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Petition_Sup_Doc> docs) {
		this.docs = docs;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<College> getColleges() {
		return colleges;
	}

	public void setColleges(List<College> colleges) {
		this.colleges = colleges;
	}

	public Student getCheckStatusStudent() {
		return checkStatusStudent;
	}

	public void setCheckStatusStudent(Student checkStatusStudent) {
		this.checkStatusStudent = checkStatusStudent;
	}

	public StudentPetition getEditedStudentPetition() {
		return editedStudentPetition;
	}

	public void setEditedStudentPetition(StudentPetition editedStudentPetition) {
		this.editedStudentPetition = editedStudentPetition;
	}

	public List<StudentPetition> getStudentPetitions() {
		return studentPetitions;
	}

	public void setStudentPetitions(List<StudentPetition> studentPetitions) {
		this.studentPetitions = studentPetitions;
	}

	public StudentPetition getStudentPetition() {
		return studentPetition;
	}

	public void setStudentPetition(StudentPetition studentPetition) {
		this.studentPetition = studentPetition;
	}

	public Student getStudent1() {
		return student1;
	}

	public void setStudent1(Student student1) {
		this.student1 = student1;
	}

	public List<Petition_Type> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(List<Petition_Type> requestTypes) {
		this.requestTypes = requestTypes;
	}

	public List<SchoolSession> getSemester() {
		return semester;
	}

	public void setSemester(List<SchoolSession> semester) {
		this.semester = semester;
	}

	public String goToForm() {
		StudentDao studentDao = new StudentDao();
		student1 = new Student();
		studentPetition = new StudentPetition();
		try {
			DummyLogin login = (DummyLogin) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("login");
			Login_Field login_Field = login.getLoginField();
			student1.setSid(login_Field.getNetId());
			List<Petition_Type> petitionTypes = studentDao.getRequestTypes();
			List<SchoolSession> schoolSessions = studentDao.getSchoolSessions();
			colleges = studentDao.getColleges();

			requestTypes = (petitionTypes != null && !petitionTypes.isEmpty()) ? petitionTypes
					: new ArrayList<Petition_Type>();
			semester = (schoolSessions != null && !schoolSessions.isEmpty()) ? schoolSessions
					: new ArrayList<SchoolSession>();

			this.colleges = studentDao.getColleges();
			states = studentDao.getStates();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error Occured !!",
							"Critical Error Occured !!"));
		}
		return "/student/studentPetitionForm.xhtml?faces-redirect=true";
	}

	public void handleFileUpload(FileUploadEvent event) {
		Petition_Sup_Doc doc = new Petition_Sup_Doc();
		doc.setDocument_Attachment(event.getFile());
		docs.add(doc);
		FacesMessage msg = new FacesMessage(event.getFile().getFileName()
				+ " is uploaded.", "Successful");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String loadStudentPetitions() {
		StudentDao studentDao = new StudentDao();
		DummyLogin dummyLogin = (DummyLogin) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("login");
		try {
			checkStatusStudent = studentDao.getStudentPetitions(dummyLogin
					.getLoginField().getNetId());

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error Occured !!",
							"Critical Error Occured !!"));
		}
		return "/student/studentCheckStatus.xhtml?faces-redirect=true";
	}

	public String editStudentPetition() {
		StudentPetition studentPetition = editedStudentPetition;
		Student currStudent = checkStatusStudent;
		student1 = currStudent;
		this.studentPetition = studentPetition;
		StudentDao studentDao = new StudentDao();
		try {
			List<Petition_Type> petitionTypes = studentDao.getRequestTypes();
			List<SchoolSession> schoolSessions = studentDao.getSchoolSessions();
			List<College> colleges = studentDao.getColleges();
			DummyLogin login = (DummyLogin) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("login");
			Login_Field login_Field = login.getLoginField();
			student1.setSid(login_Field.getNetId());
			this.colleges = (colleges != null) ? colleges
					: new ArrayList<College>();
			requestTypes = (petitionTypes != null) ? petitionTypes
					: new ArrayList<Petition_Type>();
			semester = (schoolSessions != null) ? schoolSessions
					: new ArrayList<SchoolSession>();
			states = studentDao.getStates();

//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_INFO,
//							"Petition successfully updates", "Success"));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error", "Critical Error Occured !!"));
		}
		return "studentPetitionForm.xhtml?faces-direct=true";
	}

	public String enterStudentPetitionDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		StudentDao studentDao = new StudentDao();
		try {
			studentDao.checkStudentExistence(student1);
			studentPetition.setSID(student1.getSid());
			studentPetition.setPetitionSupDocs(docs);
			if(studentPetition.getPetition_Number() == null ) {
				studentDao.insertStudentPetition(studentPetition);
			} else {
				studentDao.updateStudentPetition(studentPetition);
			}
			

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Petition successfully raised, please check the status under"
									+ " \"check status\" below", "Success"));

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Critical Error Occured !!",
					"Critical Error Occured !!"));
		}
		return null;
	}
	
	public String validateStudentInfo(){
		Date date = new Date();
		String monthStr = null;
		int year = date.getYear();
		int month = date.getMonth();
		if(month >=1 && month <= 5) {
			monthStr = "Spring";
		} else if (month >= 6 && month <=8) {
			monthStr = "Summer";
		} else if (month >= 9 && month <=12 ) {
			monthStr = "Fall";
		}
		Integer petition_year = new Integer(studentPetition.getSession_Year());
		if((year-petition_year) < 2) {
			
		}
		
		return "/student/studentPersonalStatement.xhtml?faces-redirect=true";
	}
}
