package com.principalmvl.lojackmykids.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slim3.datastore.Datastore;

import com.google.gson.Gson;
import com.principalmvl.lojackmykids.meta.ContactMeta;
import com.principalmvl.lojackmykids.model.Contact;

public class LookupUserServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1917454031684477812L;

	private static final String PARAMETER_REG_ID = "regId";
	private static final String PARAMETER_EMAIL = "email";
	private static final Logger log = Logger.getLogger(LookupUserServlet.class
			.getName());

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		log.warning("[REGISTER SERVLET] Req:" + req.toString());
		String regId = "";
		String email = "";
		
		//EntityManager emf = EMF.get().createEntityManager();

		resp.setContentType("application/json");
		resp.setHeader("Cache-Control", "nocache");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();

		Gson gson = new Gson();

		try {
			regId = getParameter(req, PARAMETER_REG_ID);
			email = getParameter(req, PARAMETER_EMAIL);

			ContactMeta c = new ContactMeta();
			Contact contact = Datastore.query(c).filter(c.email.equal(email)).asSingle();

			if (contact == null) {
				
			} else {
				out.print(gson.toJson(contact));
			}
			//emf.persist(contact);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.flush();
		//	emf.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		doGet(req, resp);

	}

}
