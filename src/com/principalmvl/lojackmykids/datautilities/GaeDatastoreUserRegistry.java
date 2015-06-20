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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.principalmvl.lojackmykids.authentication.AppRole;
import com.principalmvl.lojackmykids.interfaces.UserRegistry;
import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.server.LookupUserServlet;

/**
 * UserRegistry implementation which uses GAE's low-level Datastore APIs.
 *
 * @author Luke Taylor
 */

public class GaeDatastoreUserRegistry implements UserRegistry {
	
	private static final Logger log = Logger.getLogger(LookupUserServlet.class
			.getName());
	
	private static final String USER_TYPE = "GAEUser";
	private static final String USER_FORENAME = "forename";
	private static final String USER_SURNAME = "surname";
	private static final String USER_NICKNAME = "nickname";
	private static final String USER_EMAIL = "email";
	private static final String USER_ENABLED = "enabled";
	private static final String USER_AUTHORITIES = "authorities";
	private static final String PASSWORD = "password";
	private static final String REGID = "regid";
	
	@Override
	public GaeUser findUser(String userId) {

		//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();//Part of old implementation

		try {			
			//Entity contact = datastore.get(key); //Old implementation
			Key key = Datastore.createKey(GaeUser.class, userId);
			log.info("Finding user by key" );
			log.info("Key is : "+key.toString());
			GaeUser gaeUser = Datastore.get(GaeUser.class, key); //my implementation
			
			assert gaeUser != null;
			
			long binaryAuthorities= gaeUser.getRolesBit();
			//long binaryAuthorities = (Long) contact.getProperty(USER_AUTHORITIES);

			Set<AppRole> roles = EnumSet.noneOf(AppRole.class);

			for (AppRole r : AppRole.values()) {
				
				if ((binaryAuthorities & (1 << r.getBit())) != 0) {
					roles.add(r);
				}
				
				log.warning("Added role to user: "+ r);
			
				//log.warning("Testing: r :"+r);
			}
						
			return gaeUser;

		}
		
		catch (Exception e) {
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
		//Key key = KeyFactory.createKey(USER_TYPE, newUser.getUserId());
		Key key = Datastore.createKey(GaeUser.class, newUser.getUserId());
		newUser.setKey(key);
		
		log.warning("Attempting to create new user " + newUser);
		Collection<AppRole> roles = newUser.getAuthorities();

		long binaryAuthorities = 0;

		for (AppRole r : roles) {
			binaryAuthorities |= 1 << r.getBit();
		}
		
		log.info("BinaryAuthorities: "+ Long.toString(binaryAuthorities));
		newUser.setRolesBit(binaryAuthorities);
		Datastore.put(newUser);
		
	}
	
	public static List<Contact> getUsers (ModelMap model) {
		List<Contact> users = Datastore.query(Contact.class).asList();

		model.addAttribute("userList", users);

		return users;
	}


	@Override
	public GaeUser findUserByQuery(String email) {
		GaeUser gaseUser = Datastore.query(GaeUser.class)
			    .filter("email", FilterOperator.EQUAL, email)
			    .asSingle();
		return gaseUser;
	}

}
