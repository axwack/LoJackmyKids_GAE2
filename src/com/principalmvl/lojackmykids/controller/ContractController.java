package com.principalmvl.lojackmykids.controller;
import java.util.List;

import org.apache.log4j.Logger;
import org.slim3.datastore.Datastore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.model.GaeUser;


@Controller
@RequestMapping("/user")
public class ContractController {

	private static final Logger log = Logger.getLogger(ContractController.class);

	// get all users
	
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		
		public @ResponseBody List<Contact> listCustomer( ModelMap model) {
			log.warn("In the list return on the UserController");
			List<Contact> users = Datastore.query(Contact.class).asList();
			
			model.addAttribute("userList", users);
				
			return users;

		}
		@RequestMapping(value = "/list2", method = RequestMethod.GET)
		
		public @ResponseBody List<GaeUser> listGaeUser( ModelMap model) {
			log.warn("In the list return on the UserController returning GAE Users");
			List<GaeUser> users = Datastore.query(GaeUser.class).asList();
			
			model.addAttribute("userList", users);
				
			return users;

		}
}

