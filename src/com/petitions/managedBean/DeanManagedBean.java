package com.petitions.managedBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.petitions.Delegate_Decision;
import com.petitions.Delegate_DecisionPK;
import com.petitions.DummyLogin;
import com.petitions.Login_Field;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.petitionsDAO.DeanDAO;

@ManagedBean(name = "dean")
@SessionScoped
public class DeanManagedBean {

	private List<Student> students;
	private List<Student> filteredStudents;
	private Student selectedStudent;
	private StudentPetition selectedStudentPetition;
	private Delegate_Decision decision;
	private List<StreamedContent> streams = new ArrayList<StreamedContent>();

	public List<StreamedContent> getStreams() {
		return streams;
	}

	public void setStreams(List<StreamedContent> streams) {
		this.streams = streams;
	}

	public Delegate_Decision getDecision() {
		return decision;
	}

	public void setDecision(Delegate_Decision decision) {
		this.decision = decision;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public StudentPetition getSelectedStudentPetition() {
		return selectedStudentPetition;
	}

	public void setSelectedStudentPetition(
			StudentPetition selectedStudentPetition) {
		this.selectedStudentPetition = selectedStudentPetition;
	}

	public List<Student> getFilteredStudents() {
		return filteredStudents;
	}

	public void setFilteredStudents(List<Student> filteredStudents) {
		this.filteredStudents = filteredStudents;
	}


	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void listofAllPetitions() {
		FacesContext context = FacesContext.getCurrentInstance();
		DummyLogin login = (DummyLogin) context.getExternalContext()
				.getSessionMap().get("login");
		Login_Field login_Field = login.getLoginField();
		DeanDAO deanDao = new DeanDAO();
		try {
			students = deanDao.getStudentPetitions(login_Field.getNetId());
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public String viewPetition() {
		try {
			StreamedContent stream;
			List<Petition_Sup_Doc> docs = selectedStudentPetition
					.getPetitionSupDocs();
			for (Petition_Sup_Doc doc : docs) {
				InputStream is = new ByteArrayInputStream(
						(byte[]) doc.getDocument_Attachment());
				stream = new DefaultStreamedContent(is, doc.getDocument_type(),
						doc.getDocument_name());
				streams.add(stream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/department/deptPetition.xhtml?faces-redirect=true";
	}

	public String goToSubmitDecision() {
		Delegate_DecisionPK id = new Delegate_DecisionPK();
		DummyLogin login = (DummyLogin) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("login");
		Login_Field login_Field = login.getLoginField();

		id.setAssigned_Delegate_ID(login_Field.getNetId());
		id.setPetition_Number(selectedStudentPetition.getPetition_Number());
		decision = new Delegate_Decision();
		decision.setId(id);
		decision.setStudentPetition(selectedStudentPetition);
		return "/department/departAppr.xhtml?faces-redirect=true";
	}

	public String submitDeptDecision() {
		DeanDAO deanDao = new DeanDAO();
		deanDao.checkStudentPetitionStatus(decision);
		decision = new Delegate_Decision();
		FacesContext
		.getCurrentInstance()
		.addMessage(
				null,
				new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Your petition decision is successfully submitted",
						"success"));
		return null;
	}
}
