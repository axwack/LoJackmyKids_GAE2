package com.principalmvl.lojackmykids.authentication;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Luke Taylor
 */
public enum AppRole implements GrantedAuthority {
	ROLE_ADMIN(0), ROLE_NEW_USER(1), ROLE_USER(2), ROLE_ANONYMOUS(3);

	private final int bit;

	/**
	 * Creates an authority with a specific bit representation. It's important that this
	 * doesn't change as it will be used in the database. The enum ordinal is less
	 * reliable as the enum may be reordered or have new roles inserted which would change
	 * the ordinal values.
	 *
	 * @param bit the permission bit which will represent this authority in the datastore.
	 */
	AppRole(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return bit;
	}

	public String getAuthority() {
		return toString();
	}
}
