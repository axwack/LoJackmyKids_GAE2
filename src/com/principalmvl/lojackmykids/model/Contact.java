package com.principalmvl.lojackmykids.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.principalmvl.lojackmykids.authentication.AppRole;

@Model
public class Contact {

	/**
	 * Contact Class that wrappers User
	 */
	private String regId;
	private String userId;
	private String email;
	private String nickname;
	private boolean enabled;
	private Set<AppRole> authorities;
	private String firstName, lastName;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	//relationship to App Engine User class	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Attribute(listener = ModificationDate.class)
	Date updatedAt;
	private long id;
	@Attribute(listener = CreationDate.class)
	Date createdAt;
	
	public Contact(){}
	
	public Contact (String UserId){
		userId = UserId;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Attribute(primaryKey = true)
	private Key key;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public static Contact find(String email, EntityManager em) {
		javax.persistence.Query q = em
				.createQuery("Select c from Contact c where c.email = :email");
		q.setParameter("email", email);
		List<Contact> result = q.getResultList();

		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	public static Contact get(Key key) {
		return Datastore.get(Contact.class, key);
	}


	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String toString() {
		return "Contact {Email: " + getEmail() + " RegID: " + getRegId()
				+ " Key: " + getKey() + " Last Update Date: "
				+ getUpdatedAt() + " Create Date: " + getCreatedAt()
				+ " }";
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
