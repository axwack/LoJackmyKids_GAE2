package com.principalmvl.lojackmykids.interfaces;

import com.principalmvl.lojackmykids.model.GaeUser;

/**
*
* Service used to maintain a list of users who are registered with the application.
*
* @author Luke Taylor
*/

public interface UserRegistry {

	GaeUser findUser(String userId);
	
	GaeUser findUserByQuery(String email);

	void registerUser(GaeUser user);

	void removeUser(String userId);
}
