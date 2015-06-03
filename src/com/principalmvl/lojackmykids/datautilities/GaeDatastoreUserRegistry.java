package com.principalmvl.lojackmykids.datautilities;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;
import org.springframework.ui.ModelMap;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.principalmvl.lojackmykids.authentication.AppRole;
import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.model.iUserRegistry;
import com.principalmvl.lojackmykids.server.LookupUserServlet;

/**
 * UserRegistry implementation which uses GAE's low-level Datastore APIs.
 *
 * @author Luke Taylor
 */
public class GaeDatastoreUserRegistry implements iUserRegistry {
	
	private static final Logger log = Logger.getLogger(LookupUserServlet.class
			.getName());
	
	private static final String USER_TYPE = "Contact";
	private static final String USER_FORENAME = "forename";
	private static final String USER_SURNAME = "surname";
	private static final String USER_NICKNAME = "nickname";
	private static final String USER_EMAIL = "email";
	private static final String USER_ENABLED = "enabled";
	private static final String USER_AUTHORITIES = "authorities";

	@Override
	public GaeUser findUser(String userId) {
		
		Key key = KeyFactory.createKey(USER_TYPE, userId);
		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();//Part of old implementation

		try {
			//Entity contact = datastore.get(key); //Old implementation
			GaeUser contact = Datastore.get(GaeUser.class, key); //my implementation

			//long binaryAuthorities = (Long) contact.getProperty(USER_AUTHORITIES);
			Set<AppRole> appRole= contact.getAuthorities();
			log.warning("Authorities:" + appRole);
			Set<AppRole> roles = EnumSet.noneOf(AppRole.class);

			for (AppRole r : AppRole.values()) {
				if ((binaryAuthorities & (1 << r.getBit())) != 0) {
					roles.add(r);
				}
				
				log.warning("Added role to user: "+ r);
			}
						
			GaeUser gaeUser = new GaeUser(
					contact.getKey().getName(),
					contact.getNickname(),
					contact.getEmail(),
					contact.getForename(),
					contact.getSurname(), 
					roles,
					contact.isEnabled()
					);
			log.warning("Creating new user. Created: "+gaeUser);
			/*
			 * GaeUser gaeUser = new GaeUser(
					contact.getKey().getName(),
					(String) contact.getProperty(USER_NICKNAME),
					(String) contact.getProperty(USER_EMAIL),
					(String) contact.getProperty(USER_FORENAME),
					(String) contact.getProperty(USER_SURNAME), roles,
					(Boolean) contact.getProperty(USER_ENABLED));
					
			Contact contact = new Contact(
							 user.getKey().getName(),
					(String) user.getProperty(USER_NICKNAME),
					(String) user.getProperty(USER_EMAIL),
					(String) user.getProperty(USER_FORENAME),
					(String) user.getProperty(USER_SURNAME), 
					roles,
					(Boolean) user.getProperty(USER_ENABLED));
			 */
			return gaeUser;

		}
		catch (EntityNotFoundException e) {
			log.warning(userId + " not found in datastore");
			return null;
		}
	}


	public void removeUser(String userId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(USER_TYPE, userId);

		datastore.delete(key);
	}

	@Override
	public void registerUser(GaeUser newUser) {
		log.warning("Attempting to create new user " + newUser);

		Key key = KeyFactory.createKey(USER_TYPE, newUser.getUserId());

		Entity user = new Entity(key);
		user.setProperty(USER_EMAIL, newUser.getEmail());
		user.setProperty(USER_NICKNAME, newUser.getNickname());
		user.setProperty(USER_FORENAME, newUser.getForename());
		user.setProperty(USER_SURNAME, newUser.getSurname());
		user.setUnindexedProperty(USER_ENABLED, newUser.isEnabled());

		
		/*
		Contact user = new Contact();
		user.setKey(key);
		user.setEmail(newUser.getEmail());
		user.setNickname(newUser.getNickname());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEnabled(newUser.isEnabled());
*/
		Collection<AppRole> roles = newUser.getAuthorities();

		long binaryAuthorities = 0;

		for (AppRole r : roles) {
			binaryAuthorities |= 1 << r.getBit();
		}

		//user.setUnindexedProperty(USER_AUTHORITIES, binaryAuthorities);

		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Datastore.put(user);
		
	}
	
	public static List<Contact> getUsers (ModelMap model) {
		List<Contact> users = Datastore.query(Contact.class).asList();

		model.addAttribute("userList", users);

		return users;
	}

}
