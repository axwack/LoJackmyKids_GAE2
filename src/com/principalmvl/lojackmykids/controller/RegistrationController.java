package com.principalmvl.lojackmykids.controller;

import java.util.EnumSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.users.UserServiceFactory;
import com.principalmvl.lojackmykids.authentication.AppRole;
import com.principalmvl.lojackmykids.authentication.GaeUserAuthentication;
import com.principalmvl.lojackmykids.model.GaeUser;
import com.principalmvl.lojackmykids.model.iUserRegistry;
import com.principalmvl.lojackmykids.web.RegistrationForm;


/**
 * @author Luke Taylor
 */
@Controller
@RequestMapping(value = "/register.jsp")
public class RegistrationController {

	@Autowired
	private iUserRegistry registry;

	@RequestMapping(method = RequestMethod.GET)
	public RegistrationForm registrationForm() {
		return new RegistrationForm();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid RegistrationForm form, BindingResult result) {
		
		if (result.hasErrors()) {
			return null;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		GaeUser currentUser = (GaeUser) authentication.getPrincipal();
		Set<AppRole> roles = EnumSet.of(AppRole.USER);

		if (UserServiceFactory.getUserService().isUserAdmin()) {
			roles.add(AppRole.ADMIN);
		}

		GaeUser user = new GaeUser(currentUser.getUserId(), 
				currentUser.getNickname(),
				currentUser.getEmail(), 
				form.getForename(), 
				form.getSurname(), 
				roles,
				true);
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