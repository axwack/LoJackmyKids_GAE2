package com.principalmvl.lojackmykids.model;

import com.google.appengine.api.datastore.Entity;

/**
*
* Service used to maintain a list of users who are registered with the application.
*
* @author Luke Taylor
*/
public interface iUserRegistry {

	GaeUser findUser(String userId);

	void registerUser(GaeUser user);

	void removeUser(String userId);
}
