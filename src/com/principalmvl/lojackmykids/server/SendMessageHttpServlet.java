package com.principalmvl.lojackmykids.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;

public class SendMessageHttpServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6329503918429937476L;
	String AP_KEY;
	private String Endpoint = "https://android.googleapis.com/gcm/send";
	
	//private String Endpoint ="https://pushy.me/push?api_key=008e0ffd24f654dce208cd5733a1d3c6900a24d1c006faab5672d197d28a2e9e";
	
	/**
	 * API Key for Android Apps from the Google API console
	 * https://console.developers
	 * .google.com/project/apps~metal-complex-658/apiui/credential
	 */
	private static final Logger log = Logger
			.getLogger(SendMessageHttpServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.warning("[SERVLET] Req:" + req.toString());

		// 1. We need to determine how many posts come over and how many lat lng
		// positions come

		String reg_ids = req.getParameter("reg_ids");
		String lat = req.getParameter("lat");
		String lng = req.getParameter("lng");
		AP_KEY = req.getParameter("API_KEY");

		ArrayList<String> registration_ids = new ArrayList<String>();
		Map<String, Double> points = new HashMap<String, Double>();
		registration_ids.add(reg_ids);

		points.put("lat", Double.parseDouble(lat));
		points.put("lng", Double.parseDouble(lng));

		//Save the GPS Data to Google Data store
		saveChildGPSPosition(points);

		GSONObject gpsPosition = new GSONObject(registration_ids, points);

		Gson gson = new Gson();

		String json = gson.toJson(gpsPosition);

		if (reg_ids != "") {
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,
					"No Registration ID's");
		}

		Enumeration<String> requestParameters = req.getParameterNames();
		while (requestParameters.hasMoreElements()) {
			String paramName = (String) requestParameters.nextElement();
			log.warning("Request Parameter Name: " + paramName + ", Value - "
					+ req.getParameter(paramName));
		}


		// Send the GPS coordinate to the listener as a JSON object
		com.google.appengine.api.urlfetch.HTTPResponse gcmResponse = sendNotificationRequestToGcm(json);

		if (gcmResponse.getResponseCode() == 400) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Only applies for JSON requests. " + "HTTP RESPONSE: "
							+ new String(gcmResponse.getContent()));
		} else {
			resp.setStatus(HttpServletResponse.SC_OK, "Received from GCM.");
		}

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
		 */
	}
	
	private void saveChildGPSPosition(Map<String, Double> gpsPoints){

			GPSPosition gpsObject = new GPSPosition(gpsPoints.get("lat"), gpsPoints.get("lng"));		
			
			Transaction tx = Datastore.beginTransaction();
		
			Datastore.put(tx, gpsObject);
			tx.commit();

			
}
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	private com.google.appengine.api.urlfetch.HTTPResponse sendNotificationRequestToGcm(
			String json) {
		log.warning("In sendNotificationRequestToGcm method!");

		log.warning("JSON payload: " + json);

		com.google.appengine.api.urlfetch.HTTPResponse response = null;
		URL url;
		HTTPRequest httpRequest;

		try {
			// GCM_URL = https://android.googleapis.com/gcm/send
			url = new URL(Endpoint);
			
			
			httpRequest = new HTTPRequest(url, HTTPMethod.POST);
			httpRequest.addHeader(new HTTPHeader("Content-Type",
					"application/json"));
			httpRequest.addHeader(new HTTPHeader("Authorization", "key="
					+ AP_KEY));
			httpRequest.setPayload(json.getBytes("UTF-8"));
			log.warning("Sending POST request to: " + Endpoint);

			response = URLFetchServiceFactory.getURLFetchService().fetch(
					httpRequest);

			log.warning("Status: " + response.getResponseCode());

			List<HTTPHeader> hdrs = response.getHeaders();

			for (HTTPHeader header : hdrs) {
				log.warning("Header: " + header.getName());
				log.warning("Value:  " + header.getValue());
			}

		} catch (UnsupportedEncodingException e1) {
			log.severe("UnsupportedEncodingException" + e1.getMessage());
		} catch (MalformedURLException e1) {
			log.severe("MalformedURLException" + e1.getMessage());
		} catch (IOException e) {
			log.severe("URLFETCH IOException" + e.getMessage());
		}
		return response;
	}
}
