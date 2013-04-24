/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.juang.hello.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

import me.juang.hello.api.EchoService;

public class EchoClient implements BundleActivator {
	private ServiceTracker<EchoService, Object> tracker = null;
	private EchoService echoService = null;
	private EchoThread thread = null;

    public void start(BundleContext context) throws InvalidSyntaxException {
        System.out.println("Starting the client bundle");
    	thread = new EchoThread();
    	tracker = new ServiceTracker<EchoService, Object>(
    			context, 
    			context.createFilter("(objectclass="+EchoService.class.getName()+")"), 
    			null);
    	tracker.open();
    	thread.start();
    }

    public void stop(BundleContext context) throws InterruptedException {
        System.out.println("Stopping the client bundle");
        thread.stopThread();
        thread.join();
        tracker.close();
    }
	
	private class EchoThread extends Thread {
		private boolean isActive = true;
		
	    public void run() {
	        System.out.println("Run the client bundle");
	        while(isActive) {
	        	echoService = (EchoService)tracker.getService();
	        	if(echoService!=null) echoService.PrintEcho();
    	    	try {
    	    		Thread.sleep(5000);
    			} catch (Exception e) {
    				System.out.println("Waw.. an exception happened!");
    			}
	        }
	    }
	    
	    public void stopThread() {
	    	isActive = false;
	    }
	}
}