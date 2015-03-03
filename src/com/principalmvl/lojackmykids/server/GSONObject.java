package com.principalmvl.lojackmykids.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GSONObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Double> data;
	private ArrayList<String> registration_ids;


	public GSONObject(ArrayList<String> regId, Map<String,Double> data) {

		this.registration_ids = regId;
		this.data=data;
	}
	public List<String> getRegId() {
		return registration_ids;
	}

	public void setRegId(ArrayList<String> regId) {
		this.registration_ids = regId;
	}


	
}
