package com.principalmvl.lojackmykids.authentication;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.server.LookupUserServlet;



/**
 * @author Luke Taylor
 */
public class GaeAuthenticationFilter extends GenericFilterBean {
	private static final String REGISTRATION_URL = "/register.jsp";

	private static final Logger log = Logger.getLogger(LookupUserServlet.class.getName());
	
	private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager;
	
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User googleUser = UserServiceFactory.getUserService().getCurrentUser();
		
		if (authentication != null && !loggedInUserMatchesGaeUser(authentication, googleUser)) {
			SecurityContextHolder.clearContext();
			authentication = null;
			((HttpServletRequest) request).getSession().invalidate();
		}

		if (authentication == null) {
			
			if (googleUser != null) {
				
				log.warning("Currently logged on to GAE as user " + googleUser);
				log.warning("Authenticating to Spring Security");
				// User has returned after authenticating via GAE. Need to authenticate
				// through Spring Security.
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
				token.setDetails(ads.buildDetails((HttpServletRequest) request));

				try {
					log.warning("authenticating...");
					authentication = authenticationManager.authenticate(token);	
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					if (authentication.getAuthorities().contains(AppRole.ROLE_NEW_USER)) {
						
						log.warning("New user authenticated. Redirecting to registration page");
						((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);
						return;
					}

				}
				catch (AuthenticationException e) {
					failureHandler.onAuthenticationFailure((HttpServletRequest) request,
							(HttpServletResponse) response, e);
					log.warning("Authentication error...");
					log.warning(e.toString());
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	private boolean loggedInUserMatchesGaeUser(Authentication authentication,
			User googleUser) {
		
		assert authentication != null;
		
		if (googleUser == null) {
			// User has logged out of GAE but is still logged into application
			return false;
		}

		GaeUser contact = (GaeUser) authentication.getPrincipal();
		log.warning("GaeUser: " + contact.toString());
		
		if (!contact.getEmail().equals(googleUser.getEmail())) {
			log.warning("returning false");
			return false;
			
		}
		log.warning("returning true");
		return true;

	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		Assert.notNull(authenticationManager, "AuthenticationManager must be set");
	}
	
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

}