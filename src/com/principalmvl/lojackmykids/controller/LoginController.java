package com.principalmvl.lojackmykids.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slim3.datastore.Datastore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.principalmvl.lojackmykids.meta.ContactMeta;
import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.server.LookupUserServlet;


@Controller
public class LoginController {
	
	private static final Logger log = Logger.getLogger(LookupUserServlet.class
			.getName());
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String landing() {
		return "landing";
	}

	@RequestMapping(value = "/home.jsp", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/disabled.jsp", method = RequestMethod.GET)
	public String disabled() {
		return "disabled";
	}

	@RequestMapping(value = "/logout.jsp", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().invalidate();

		String logoutUrl = UserServiceFactory.getUserService().createLogoutURL(
				"/loggedout.jsp");

		response.sendRedirect(logoutUrl);
	}

	@RequestMapping(value = "/loggedout.jsp", method = RequestMethod.GET)
	public String loggedOut() {
		return "loggedout";
	}
	
	
	
	@RequestMapping("/")
	public String Login() {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		log.warning("Current user is: " + user);
		
		if (user !=null && !this.isUserInDb(user)){
			
			Contact newUserContact = new Contact();						
			newUserContact.setUserId(user.getUserId());
			
			Datastore.put(newUserContact);
			log.warning("Redirecting to Home.jsp");
			return "home";
		
		} else if (user != null){
			
			log.warning("Redirecting to second Home.jsp");
			return "home";
		
		} else {
			
			log.warning("Redirecting to Login.jsp");
			return "login/Login";
		}
	}
	
	@RequestMapping("/ListUsers")
	public String ListUser() {

		return "users/ListUsers";

	}
	
	
	
	private boolean isUserInDb(User _user){
		
		ContactMeta c = ContactMeta.get();
		
		Contact contact = Datastore.query(c)
			.filter("email", FilterOperator.EQUAL, _user.getEmail())
				.asSingle();
		
		log.warning("User in Datastore: "+ contact);
		
		
		if (contact != null ){
			
			return true;
		}
		
		else {
			
			return false;
		}	
	}
	

}
