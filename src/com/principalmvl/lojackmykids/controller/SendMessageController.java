package com.principalmvl.lojackmykids.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.principalmvl.lojackmykids.datautilities.AppengineDataUtilities;
import com.principalmvl.lojackmykids.server.GSONObject;
import com.principalmvl.lojackmykids.server.SendMessageHttpServlet;


@Controller
public class SendMessageController {
	private static final long serialVersionUID = 6329503918429937476L;
	String AP_KEY="AIzaSyAjYdpOzQcDqIWtHn1zzRvJipSi5L2c5eY";
	private String Endpoint = "https://gcm-http.googleapis.com/gcm/send";
	private static final Logger log = Logger.getLogger(SendMessageHttpServlet.class.getName());
	/*
	@RequestMapping (value = {"/SendMessage"}, method = RequestMethod.POST)
	public String sendMessage(@RequestParam Map<String,String> allRequestParams) {

		// 1. We need to determine how many posts come over and how many lat lng
		// positions come

		//String reg_ids = req.getParameter("reg_ids");
		//String lat = req.getParameter("lat");
		//String lng = req.getParameter("lng");
		//AP_KEY = req.getParameter("API_KEY");

		ArrayList<String> registration_ids = new ArrayList<String>();
		Map<String, Double> points = new HashMap<String, Double>();
		//registration_ids.add(reg_ids);

		//points.put("lat", Double.parseDouble(lat));
		//points.put("lng", Double.parseDouble(lng));

		//Save the GPS Data to Google Data store
		//saveChildGPSPosition(points);

		GSONObject gpsPosition = new GSONObject(registration_ids, points);

		Gson gson = new Gson();

		String json = gson.toJson(gpsPosition);

	//	if (reg_ids != "") {
	//		resp.setStatus(HttpServletResponse.SC_OK);
	//	} else {
	//		resp.sendError(HttpServletResponse.SC_NOT_FOUND,
	//				"No Registration ID's");
		//}

		//Enumeration<String> requestParameters = req.getParameterNames();
		//while (requestParameters.hasMoreElements()) {
	//		String paramName = (String) requestParameters.nextElement();
	//		log.warning("Request Parameter Name: " + paramName + ", Value - "
	//				+ req.getParameter(paramName));
		//}


		// Send the GPS coordinate to the listener as a JSON object
		//com.google.appengine.api.urlfetch.HTTPResponse gcmResponse = CloudMessagingUtilities.sendNotificationRequestToGcm(json);

		//if (gcmResponse.getResponseCode() == 400) {
		//	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
		//			"Only applies for JSON requests. " + "HTTP RESPONSE: "
		//					+ new String(gcmResponse.getContent()));
		//} else {
	//	//	resp.setStatus(HttpServletResponse.SC_OK, "Received from GCM.");
		//}

		/*
		 * Enumeration<String> requestAttributes = req.getAttributeNames();
		 * while (requestAttributes.hasMoreElements()) { String attributeName =
		 * (String) requestAttributes.nextElement();
		 * log.warning("Request Attribute Name: " + attributeName + ", Value - "
		 * + (req.getAttribute(attributeName)).toString()); }
		 */

		/*
		 * resp.setContentType("Content-Type:application/json");
		 * resp.setHeader("Authorization", "key="+AP_KEY); RequestDispatcher
		 * dispatcher = req.getRequestDispatcher(GCMEndpoint);
		 * dispatcher.forward(req, resp); log.warning(resp.toString());
		 
		
		
		return "multiselect/SendMessage";
	}
	*/
	@RequestMapping(value = {"/sendUser"}, method = RequestMethod.GET)
	public String sendUser(Model model) {
		log.warning("Users: "+AppengineDataUtilities.getUsers(model).toString());
	    model.addAttribute("sendUsers", AppengineDataUtilities.getUsers(model));

	    return "sendmessage/SendMessage";
	}

}
