package com.petitions.managedBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.petitions.DummyLogin;
import com.petitions.Login_Field;
import com.petitions.Petition_Committee;
import com.petitions.Petition_CommitteePK;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.petitionsDAO.ReviewerDao;

@ManagedBean(name = "reviewer")
@SessionScoped
public class ReviewerBean {

	private List<Student> students;
	private Student selectedStudent;
	private StudentPetition selectedStudentPetition;
	private Petition_Committee reviewerDecision = new Petition_Committee();
	private List<StreamedContent> streams = new ArrayList<StreamedContent>();

	public List<StreamedContent> getStreams() {
		return streams;
	}

	public void setStreams(List<StreamedContent> streams) {
		this.streams = streams;
	}

	public Petition_Committee getReviewerDecision() {
		return reviewerDecision;
	}

	public void setReviewerDecision(Petition_Committee reviewerDecision) {
		this.reviewerDecision = reviewerDecision;
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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void listAllPetitions() {
		FacesContext context = FacesContext.getCurrentInstance();
		DummyLogin login = (DummyLogin) context.getExternalContext()
				.getSessionMap().get("login");
		Login_Field login_Field = login.getLoginField();
		ReviewerDao reviewerDao = new ReviewerDao();
		try {
			students = reviewerDao.getStudentPetitions(login_Field.getNetId());
		} catch (SQLException e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Critical Error Occured !!",
					"Critical Error Occured !!"));
		}
	}

	public String goToReviewerDecision() {
		FacesContext context = FacesContext.getCurrentInstance();
		Petition_CommitteePK id = new Petition_CommitteePK();
		DummyLogin login = (DummyLogin) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("login");
		Login_Field login_Field = login.getLoginField();

		id.setCommittee_Emp_ID(login_Field.getNetId());
		id.setPetition_Number(selectedStudentPetition.getPetition_Number());
		reviewerDecision.setId(id);
		reviewerDecision.setStudentPetition(selectedStudentPetition);
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
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Critical Error Occured !!",
					"Critical Error Occured !!"));
		}
		return "/reviewer/reviewerPetition.xhtml?faces-redirect=true";
	}

	public String submitReviewerDecision() {
		FacesContext context = FacesContext.getCurrentInstance();
		ReviewerDao reviewerDao = new ReviewerDao();
		try {
			reviewerDao.checkReviewerDecisionExists(reviewerDecision);
			reviewerDecision = new Petition_Committee();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Decision successfully updated", "success"));

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Critical Error Occured !!",
					"Critical Error Occured !!"));
		}
		return null;
	}
}
