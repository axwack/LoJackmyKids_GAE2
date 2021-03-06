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

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.principalmvl.lojackmykids.model.Contact;
import com.google.appengine.api.datastore.*;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {

	private static final Logger log = Logger.getLogger(SendAllMessagesServlet.class.getName());
	private final int MULTICAST_SIZE=1000;
	/**
	 * Processes the request to add a new message.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<Contact> devices = DatastoreServerHelper.getDevices();
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			log.warning("Req: "+req.getParameter("registration_ids"));
			//Queue queue = QueueFactory.getQueue("gcm");
			Queue queue = QueueFactory.getDefaultQueue();
			
			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			if (devices.size() == 1) {
				// send a single message using plain post
				String device = devices.get(0).getRegId();
				queue.add(withUrl("/send").param(
						SendMessageServlet.PARAMETER_DEVICE, device));
				status = "Single message queued for registration id " + device;
			} else {
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit)
				int total = devices.size();
				List<Contact> partialDevices = new ArrayList<Contact>(total);
				int counter = 0;
				int tasks = 0;
				for (Contact device : devices) {
					counter++;
					partialDevices.add(device);
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE
							|| counter == total) {
						String multicastKey = DatastoreServerHelper
								.createMulticast(partialDevices);
						logger.fine("Queuing " + partialSize
								+ " devices on multicast " + multicastKey);
						TaskOptions taskOptions = TaskOptions.Builder
								.withUrl("/send")
								.param(SendMessageServlet.PARAMETER_MULTICAST,
										multicastKey).method(Method.POST);
						queue.add(taskOptions);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Queued tasks to send " + tasks
						+ " multicast messages to " + total + " devices";
			}
		}
		
		getServletContext().getRequestDispatcher("/home").forward(req, resp);
	}

}
