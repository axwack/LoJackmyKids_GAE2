package com.principalmvl.lojackmykids.controller;

import java.util.List;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.slim3.datastore.Datastore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.server.LookupUserServlet;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger log = Logger.getLogger(UserController);
	log.warn("In UserController");
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String getAddUserPage(ModelMap model) {

		return "add";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, ModelMap model) {

		/*String name = request.getParameter("name");
		String email = request.getParameter("email");

		Key userKey = KeyFactory.createKey("User", name);

		Contact user = new Contact("User", userKey);

		user.setProperty("name", name);
		user.setProperty("email", email);

		Datastore.put(user);
*/
		return new ModelAndView("redirect:list");
	}

	// get all users
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	
	public @ResponseBody List<Contact> listCustomer( ModelMap model) {
		log.warn("In the list return on the UserController");
		List<Contact> users = Datastore.query(Contact.class).asList();
		
		model.addAttribute("userList", users);
			
		return users;

	}
}
