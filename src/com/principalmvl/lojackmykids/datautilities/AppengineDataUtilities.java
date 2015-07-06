package com.principalmvl.lojackmykids.datautilities;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.springframework.ui.Model;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.principalmvl.lojackmykids.meta.GaeUserMeta;
import com.principalmvl.lojackmykids.model.Contact;
import com.principalmvl.lojackmykids.model.GaeUser;

public class AppengineDataUtilities {

	public static List<Contact> getUsers (Model model) {
		List<Contact> users = Datastore.query(Contact.class).asList();
		return users;
	}
	
	public static GaeUser getUserByEmail(String email){
		
		GaeUserMeta g = GaeUserMeta.get();
		
		return Datastore.query(g).filter("email", FilterOperator.EQUAL, email).asSingle();
	}
}
