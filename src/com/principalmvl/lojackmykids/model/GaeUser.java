package com.principalmvl.lojackmykids.model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.principalmvl.lojackmykids.authentication.AppRole;
import com.principalmvl.lojackmykids.security.Password;

/**
 * Custom user object for the application.
 *
 * @author Luke Taylor
 */
@Model
public class GaeUser implements Serializable {

	@Attribute(primaryKey = true)
	private Key key;
	
	private String password;
	private String regId;
	private String userId;
	private String email;
	private String nickname;
	private String forename;
	private String surname;
	private Set<AppRole> authorities;
	private long rolesBit;
	private boolean enabled;
	@Attribute(listener = ModificationDate.class)
	Date updatedAt;

	@Attribute(listener = CreationDate.class)
	Date createdAt;

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		try {
			String hashSaltPass = Password.getSaltedHash(password);
			this.password = hashSaltPass;
		} catch (Exception e) {
			System.out.println("Error hashing password: " + e.getMessage());
		}
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getRolesBit() {
		return rolesBit;
	}

	public void setRolesBit(long rolesBit) {
		this.rolesBit = rolesBit;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	@SuppressWarnings("unchecked")
	public Set /* Collection */<AppRole> getAuthorities() {
		return authorities;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Pre-registration constructor.
	 *
	 * Assigns the user the "NEW_USER" role only.
	 */

	public GaeUser() {
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public GaeUser(String userId, String nickname, String email) {
		this.userId = userId;
		this.nickname = nickname;
		this.authorities = EnumSet.of(AppRole.ROLE_NEW_USER);
		this.forename = null;
		this.surname = null;
		this.email = email;
		this.enabled = true;
		this.createdAt = this.updatedAt = getUpdatedAt();
	}

	/**
	 * Post-registration constructor
	 */
	public GaeUser(String userId, String nickname, String email,
			String forename, String surname, Set<AppRole> authorities,
			boolean enabled, String password, String regid) {
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.authorities = authorities;
		this.forename = forename;
		this.surname = surname;
		this.enabled = enabled;
		this.setPassword(password);
		this.regId = regid;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "GaeUser{" + "userId='" + userId + '\'' + ", nickname='"
				+ nickname + '\'' + ", forename='" + forename + '\''
				+ ", surname='" + surname + '\'' + ", authorities="
				+ authorities + ", email=" + email + ", isEnabled="
				+ enabled + ", Key="+key + ", createdAt="+createdAt + ", updatedAt=" + updatedAt+ '}';
	}
}
