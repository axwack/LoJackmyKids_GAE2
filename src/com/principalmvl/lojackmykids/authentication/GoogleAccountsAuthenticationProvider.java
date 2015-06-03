package com.principalmvl.lojackmykids.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;
import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.model.iUserRegistry;

public class GoogleAccountsAuthenticationProvider implements
		AuthenticationProvider {

	    private iUserRegistry userRegistry;

	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	       
	    	User googleUser = (User) authentication.getPrincipal();

			GaeUser user = userRegistry.findUser(googleUser.getUserId());

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

	        return new GaeUserAuthentication(user, authentication.getDetails()); //Entry point to authenticate the page
	    }

	    public final boolean supports(Class<?> authentication) {
	        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	    }

	    public void setUserRegistry(iUserRegistry userRegistry) {
	        this.userRegistry = userRegistry;
	    }
	

}
