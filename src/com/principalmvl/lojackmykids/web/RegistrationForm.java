package com.principalmvl.lojackmykids.web;

import com.principalmvl.lojackmykids.validators.Forename;
import com.principalmvl.lojackmykids.validators.Surname;

/**
 * @author Luke Taylor
 */
public class RegistrationForm {
	@Forename
	private String forename;
	@Surname
	private String surname;
	private String email;
	private String password;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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