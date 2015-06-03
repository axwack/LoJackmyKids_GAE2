package com.principalmvl.lojackmykids.web;



/**
 * @author Luke Taylor
 */
public class RegistrationForm {
	//@Forename [Look at Spring Validators for this]
	private String forename;
	//@Surname
	private String surname;

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}