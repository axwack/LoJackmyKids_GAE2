package com.principalmvl.lojackmykids.controller;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slim3.datastore.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserServiceFactory;
import com.principalmvl.lojackmykids.authentication.AppRole;
import com.principalmvl.lojackmykids.authentication.GaeUserAuthentication;
import com.principalmvl.lojackmykids.interfaces.UserRegistry;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.server.LookupUserServlet;
import com.principalmvl.lojackmykids.web.RegistrationForm;


/**
 * @author Luke Taylor
 */
@Controller
@RequestMapping(value = "/register.jsp")
public class RegistrationController {

	private static final Logger log = Logger.getLogger(LookupUserServlet.class
			.getName());
	
	@Autowired
	private UserRegistry registry;
	
	@RequestMapping(method = RequestMethod.GET)
	public RegistrationForm registrationForm(HttpServletRequest request,HttpServletResponse response) {
		
		return new RegistrationForm();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid RegistrationForm form, BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
			
		if (result.hasErrors()) {
			return null;
		}
		//HttpSession session = result.getSession(true);
		log.warning(session.getAttribute("SPRING_SECURITY_CONTEXT").toString());
	
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		GaeUser currentUser = (GaeUser) authentication.getPrincipal();
		
		log.warning("Current User: "+currentUser.toString());
		
		Set<AppRole> roles = EnumSet.of(AppRole.ROLE_USER);

		if (UserServiceFactory.getUserService().isUserAdmin()) {
			roles.add(AppRole.ROLE_ADMIN);
		}

		GaeUser user = new GaeUser(
				currentUser.getUserId(), 
				currentUser.getNickname(),
				form.getEmail(), 
				form.getForename(), 
				form.getSurname(), 
				roles,
				true,
				form.getPassword(), 
				null);

		/* for my version of user
		GaeUser user = new GaeUser(currentUser.getUserId());
		user.setNickname(currentUser.getNickname());
		user.setEmail(currentUser.getEmail()); 
		user.setFirstName(form.getForename());
		user.setLastName(form.getSurname());
		user.setAuthorities(roles);
		 */
		registry.registerUser(user);

		// Update the context with the full authentication
		SecurityContextHolder.getContext().setAuthentication(
				new GaeUserAuthentication(user, authentication.getDetails()));

		return "redirect:/home.jsp";
	}
}