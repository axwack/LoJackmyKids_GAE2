/**
 * 
 */
package com.principalmvl.lojackmykids.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;

/**
 * @author vincentlee
 *
 */
public class AuthorizeUser extends HttpServlet {

	/**
	 * 
	 */
	public AuthorizeUser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		
		User user = null;
		
	    try {
	        OAuthService oauth = OAuthServiceFactory.getOAuthService();
	        user = oauth.getCurrentUser();
	        resp.getWriter().println("Authenticated: " + user.getEmail());
	    } catch (OAuthRequestException e) {
	        resp.getWriter().println("Not authenticated: " + e.getMessage());
	    }
	}

	

}
