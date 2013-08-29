package com.petitions;

import java.io.Serializable;


/**
 * The persistent class for the Petition_Sup_Docs database table.
 * 
 */
public class Petition_Sup_Doc implements Serializable {
	private static final long serialVersionUID = 1L;

	private int document_ID;
	
	private String document_name;
	
	private String document_type;

	private Object document_Attachment;

	private Integer petition_number;
	
	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public Integer getPetition_number() {
		return petition_number;
	}

	public void setPetition_number(Integer petition_number) {
		this.petition_number = petition_number;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public Petition_Sup_Doc() {
	}

	public int getDocument_ID() {
		return this.document_ID;
	}

	public void setDocument_ID(int document_ID) {
		this.document_ID = document_ID;
	}

	public Object getDocument_Attachment() {
		return document_Attachment;
	}

	public void setDocument_Attachment(Object document_Attachment) {
		this.document_Attachment = document_Attachment;
	}

}