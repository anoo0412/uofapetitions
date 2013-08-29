package com.petitions.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
@SessionScoped
public class FileUploadController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileUploadController() {
		super();
		name = "Anusha";
	}

	public void hello() {
		System.out.println("I am Anusha");
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("hello");
	}
}
