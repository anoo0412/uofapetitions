package com.petitions;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.petitions.Login_Field;
import com.petitions.petitionsDAO.DummyLoginDAO;

@ManagedBean(name = "login")
@SessionScoped
public class DummyLogin {
	private Login_Field loginField = new Login_Field();

	/**
	 * @return the loginField
	 */
	public Login_Field getLoginField() {
		return loginField;
	}

	/**
	 * @param loginField
	 *            the loginField to set
	 */
	public void setLoginField(Login_Field loginField) {
		this.loginField = loginField;
	}

	public String validateUser() {

		try {
			DummyLoginDAO dummyDao = new DummyLoginDAO();
			loginField = dummyDao.validateUser(loginField.getLoginName(),
					loginField.getLoginPassword());

			if (loginField.getRole() == null) {
				FacesContext.getCurrentInstance().addMessage(
						null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login / password",
								"Error"));
				return null;
			} else if (loginField.getRole().equalsIgnoreCase("student")) {
				return "/student/student.xhtml?faces-redirect=true";
			} else if (loginField.getRole().equalsIgnoreCase("Dean_Delegate")) {
				return "/department/deptHome.xhtml?faces-redirect=true";
			} else if (loginField.getRole()
					.equalsIgnoreCase("Commitee_Members")) {
				return "/reviewer/reviewerHome.xhtml?faces-redirect=true";
			} else if (loginField.getRole().equalsIgnoreCase("Administrator")) {
				return "/admin/admin.xhtml?faces-redirect=true";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String Logout()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
}
