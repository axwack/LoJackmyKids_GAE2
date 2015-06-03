package com.principalmvl.jojackmykids.messaging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;
import com.principalmvl.lojackmykids.server.GSONObject;
import com.principalmvl.lojackmykids.server.SendMessageHttpServlet;



public class CloudMessagingUtilities {
	private static final Logger log = Logger
			.getLogger(SendMessageHttpServlet.class.getName());
	static String AP_KEY;
	
	private static String Endpoint = "https://android.googleapis.com/gcm/send";
	
	public CloudMessagingUtilities() {
		// TODO Auto-generated constructor stub
	}
	

	

	public static com.google.appengine.api.urlfetch.HTTPResponse sendNotificationRequestToGcm(String json) {

		log.warning("JSON payload: " + json);

		com.google.appengine.api.urlfetch.HTTPResponse response = null;
		URL url;
		HTTPRequest httpRequest;

		try {
			// GCM_URL = https://android.googleapis.com/gcm/send
			url = new URL(Endpoint);			
			
			httpRequest = new HTTPRequest(url, HTTPMethod.POST);
			httpRequest.addHeader(new HTTPHeader("Content-Type", "application/json"));
			httpRequest.addHeader(new HTTPHeader("Authorization", "key="+ AP_KEY));
			httpRequest.setPayload(json.getBytes("UTF-8"));
			
			log.warning("Sending POST request to: " + Endpoint);

			response = URLFetchServiceFactory.getURLFetchService().fetch(httpRequest);

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
