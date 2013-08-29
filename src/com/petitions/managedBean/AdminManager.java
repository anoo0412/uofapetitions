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

import com.petitions.Committee_Member;
import com.petitions.Email;
import com.petitions.PetitionDecision;
import com.petitions.Petition_Sup_Doc;
import com.petitions.Student;
import com.petitions.StudentPetition;
import com.petitions.petitionsDAO.AdminDao;
import com.petitions.util.PetitionConstants;
import com.petitions.util.PetitionsEmail;

@ManagedBean(name = "admin")
@SessionScoped
public class AdminManager {
	private List<Student> students;
	private List<Integer> selectedReviewers = new ArrayList<Integer>();
	private Integer petitionNumber;
	private Student selectedStudent;
	private StudentPetition selectedStudentPetition;
	private PetitionDecision petitionDecision = new PetitionDecision();
	private List<StreamedContent> streams = new ArrayList<StreamedContent>();

	public List<StreamedContent> getStreams() {
		return streams;
	}

	public void setStreams(List<StreamedContent> streams) {
		this.streams = streams;
	}

	public PetitionDecision getPetitionDecision() {
		return petitionDecision;
	}

	public void setPetitionDecision(PetitionDecision petitionDecision) {
		this.petitionDecision = petitionDecision;
	}

	private List<Committee_Member> committee_Members;

	public List<Committee_Member> getCommittee_Members() {
		return committee_Members;
	}

	public void setCommittee_Members(List<Committee_Member> committee_Members) {
		this.committee_Members = committee_Members;
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

	public Integer getPetitionNumber() {
		return petitionNumber;
	}

	public void setPetitionNumber(Integer petitionNumber) {
		this.petitionNumber = petitionNumber;
	}

	public List<Integer> getSelectedReviewers() {
		return selectedReviewers;
	}

	public void setSelectedReviewers(List<Integer> selectedReviewers) {
		this.selectedReviewers = selectedReviewers;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void listCollegePetitons() {
		AdminDao adminDao = new AdminDao();
		try {
			students = adminDao.getStudentPetitions();
		} catch (SQLException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error", "Critical Error Occured !!"));
		}
	}

	public String assignReviewer() {
		AdminDao adminDao = new AdminDao();
		try {
			adminDao.assignReviewers(
					selectedStudentPetition.getPetition_Number(),
					selectedReviewers);

			for (Committee_Member comm : committee_Members) {
				for (int i = 0; i < selectedReviewers.size(); i++) {
//					if (comm.getCommittee_Emp_ID() == selectedReviewers.get(i)) {
//						Petition_Committee newPetCommitteeMember = new Petition_Committee();
//						newPetCommitteeMember.setFname(comm.getMember_FName());
//						newPetCommitteeMember.setLname(comm.getMember_LName());
//						selectedStudentPetition.getPetitionCommittees().add(
//								newPetCommitteeMember);
//					}
				}
			}

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Reviewers Assigned !!", "Success Message"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error", e.getMessage()));
		}
		return null;
	}

	public String viewPetition() {
		AdminDao adminDao = new AdminDao();
		try {
			selectedReviewers = new ArrayList<Integer>();
			committee_Members = adminDao.getCommitteeMembers();
			try {
				StreamedContent stream;
				List<Petition_Sup_Doc> docs = selectedStudentPetition
						.getPetitionSupDocs();
				for (Petition_Sup_Doc doc : docs) {
					InputStream is = new ByteArrayInputStream(
							(byte[]) doc.getDocument_Attachment());
					stream = new DefaultStreamedContent(is,
							doc.getDocument_type(), doc.getDocument_name());
					streams.add(stream);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Critical Error", "Critical Error Occured !!"));
		}
		return "/admin/adminPetition.xhtml?faces-redirect=true";
	}

	public String goToPetitionDecision() {
		petitionDecision.setPetition_number(selectedStudentPetition
				.getPetition_Number());
		petitionDecision.setPetition_Decision(null);
		petitionDecision.setComment_Header(null);
		petitionDecision.setComment_Body(null);
		return "/admin/finalVerdict.xhtml?faces-redirect=true";
	}

	public String submitFinalDecision() {
		AdminDao adminDao = new AdminDao();
		adminDao.finalVerdict(petitionDecision);
		sendStudentEmail(selectedStudent, petitionDecision);
		sendRegistrarEmail(selectedStudentPetition, selectedStudent,
				petitionDecision);
		petitionDecision.setPetition_Decision(null);
		petitionDecision.setComment_Header(null);
		petitionDecision.setComment_Body(null);
		FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								"Your petition decision is successfully submitted and email has been sent to notify the student",
								"success"));
		return null;
	}

	private void sendRegistrarEmail(StudentPetition selectedStudentPetition,
			Student selectedStudent, PetitionDecision petitionDecision) {

	}

	private void sendStudentEmail(Student selectedStudent,
			PetitionDecision petitionDecision) {
		Email email = new Email();
		PetitionsEmail petitionsEmail = new PetitionsEmail();
		// load a properties file
		email.setEmailId(selectedStudent.getStudent_emailId());
		email.setSubject(selectedStudent.getStudent_Fname() + " "
				+ selectedStudent.getStudent_LName() + ":Petition Decision ");
		if (petitionDecision.getPetition_Decision()
				.equalsIgnoreCase("Approved")) {
			email.setBody(PetitionConstants.APPROVED_MAIL);
		} else {
			email.setBody(PetitionConstants.DENIED_MAIL);
		}
		petitionsEmail.SendEmail(email);
	}
	
	public String goToAdminHome() {
		return "/admin/admin.xhtml?faces-redirect=true";
	}

}
