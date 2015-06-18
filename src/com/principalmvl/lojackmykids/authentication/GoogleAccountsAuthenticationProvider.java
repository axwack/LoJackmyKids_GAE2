package com.principalmvl.lojackmykids.authentication;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;
import com.principalmvl.lojackmykids.datautilities.AppengineDataUtilities;
import com.principalmvl.lojackmykids.interfaces.UserRegistry;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.server.LookupUserServlet;

public class GoogleAccountsAuthenticationProvider implements
		AuthenticationProvider {

	    private UserRegistry userRegistry;
		private static final Logger log = Logger.getLogger(LookupUserServlet.class
				.getName());
		
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    	
	    	GaeUser user;
	    	User googleUser = (User) authentication.getPrincipal();
	    	
			if (userRegistry.findUser(googleUser.getUserId()) != null){
				
				user = userRegistry.findUser(googleUser.getUserId());
				log.warning("The User is:" + user);
				
			} else {
				
				user = userRegistry.findUserByQuery(googleUser.getEmail());
				log.warning("Couldn't find user. do by Query. The User is:" + user);
				
			}
			
			log.warning("Google: "+ googleUser + " GAEUser: "+ user);
/*
			if (user == null){ //added this send email as value
				log.warning("Couldn't find by key. Get by email...");
				user=AppengineDataUtilities.getUserByEmail(user.getEmail());
				log.warning("Contact by email: "+user);
			}
			*/
			if (user == null) {
				
				// User not in registry. Needs to register
				user = new GaeUser(
						googleUser.getUserId(), 
						googleUser.getNickname(),
						googleUser.getEmail());
						
			            /* User not in registry. Needs to register
			            user = new Contact(googleUser.getUserId());
			            user.setNickname(googleUser.getNickname());
			            user.setEmail(googleUser.getEmail());
			        */
			}

	        if (!user.isEnabled()) {
	            throw new DisabledException("Account is disabled");
	        }
	        
	        GaeUserAuthentication g = new GaeUserAuthentication(user, authentication.getDetails());

	        return new GaeUserAuthentication(user, authentication.getDetails()); //Entry point to authenticate the page
	    }

	    public final boolean supports(Class<?> authentication) {
	        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	    }

	    public void setUserRegistry(UserRegistry userRegistry) {
	        this.userRegistry = userRegistry;
	    }
	

}
