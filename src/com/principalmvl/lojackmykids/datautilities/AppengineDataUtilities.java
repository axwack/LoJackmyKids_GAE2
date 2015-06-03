package com.principalmvl.lojackmykids.datautilities;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.springframework.ui.ModelMap;

import com.principalmvl.lojackmykids.model.Contact;

public class AppengineDataUtilities {

	public static List<Contact> getUsers (ModelMap model) {
		List<Contact> users = Datastore.query(Contact.class).asList();

		model.addAttribute("userList", users);

		return users;
	}
}
