/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.principalmvl.lojackmykids.server;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.principalmvl.lojackmykids.meta.ContactMeta;
import com.principalmvl.lojackmykids.model.Contact;

/**
 * Servlet that registers a device, whose registration id is identified by
 * {@link #PARAMETER_REG_ID}.
 *
 * <p>
 * The client app should call this servlet everytime it receives a
 * {@code com.google.android.c2dm.intent.REGISTRATION C2DM} intent without an
 * error or {@code unregistered} extra.
 */
@SuppressWarnings("serial")
public class RegisterServlet extends BaseServlet {

	private static final String PARAMETER_REG_ID = "regId";
	private static final String PARAMETER_EMAIL = "email";
	private static final String PARAMETER_CREATEDATE = "createDate";
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
	private List<Contact> contactList;
	private Contact contact;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {

		log.warning("[REGISTER SERVLET] Req:" + req.toString());
		String regId = getParameter(req, PARAMETER_REG_ID);
		String email = getParameter(req, PARAMETER_EMAIL);

		Enumeration<String> requestParameters = req.getParameterNames();

		while (requestParameters.hasMoreElements()) {
			String paramName = requestParameters.nextElement();
			log.warning("Request Parameter Name: " + paramName + ", Value - "
					+ req.getParameter(paramName));
		}

		// EntityManager emf = EMF.get().createEntityManager();
		contactList = searchContact(email);

		if (contactList.isEmpty()) {
			contactList=createContact(regId, email);
			log.warning(contact.toString());
		}else {
			for (Contact c : contactList) {
		        c.setEmail(email);
		        c.setRegId(regId);
		    }
			
		}
		
		Datastore.put(contactList);

		// Datastore.register(regId); older way so we need to see if the new
		// stores the regid
		setSuccess(resp);
	}

	private List<Contact> createContact(String regId, String email) {
		contact = new Contact();
		Key key = Datastore.createKey(Contact.class, email);
		contact.setKey(key);
		contact.setEmail(email);
		contact.setRegId(regId);
		contactList.add(contact);
		return contactList;
	}

	private List<Contact> searchContact(String email) {

		ContactMeta c = new ContactMeta();

		contactList = Datastore.query(c).filter(c.email.equal(email)).asList();

		if (contactList.isEmpty()) {
			log.warning("List is empty");
		}
		return contactList;
	}
}
