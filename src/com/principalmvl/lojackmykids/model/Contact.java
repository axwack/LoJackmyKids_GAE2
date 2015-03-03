package com.principalmvl.lojackmykids.model;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

@Model
public class Contact {

	/**
	 * 
	 */
	private String email;
	private String regId;

	private org.slim3.datastore.ModelRef<Contact> contactRef = new org.slim3.datastore.ModelRef<Contact>(Contact.class);

	@Attribute(listener = ModificationDate.class)
	Date updatedAt;
	private long id;
	@Attribute(listener = CreationDate.class)
	Date createdAt;

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

	public ModelRef<Contact> getContactRef() {
		return contactRef;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
}
