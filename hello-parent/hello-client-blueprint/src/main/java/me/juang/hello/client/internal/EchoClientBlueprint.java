package me.juang.hello.client.internal;

import me.juang.hello.api.EchoService;

/**
 * Hello world!
 *
 */
public class EchoClientBlueprint 
{	
	private EchoService echoBlueprint = null;
	private EchoBlueprintThread thread;
	
    public void StartUp() {
    	System.out.println("Blueprint Client is Starting up..");
    	thread = new EchoBlueprintThread();
    	thread.start();
    }
    public void ShutDown() throws Exception {
    	System.out.println("Blueprint Client is Shutting down..");
    	thread.stopThread();
    	thread.join();
    	echoBlueprint = null;
    }

	public EchoService getEchoBlueprint() {
		return echoBlueprint;
	}
	public void setEchoBlueprint(EchoService echoBlueprint) {
		this.echoBlueprint = echoBlueprint;
	}
	
	private class EchoBlueprintThread extends Thread {
		private boolean isActive = true;
		
		public void run() {
			while(isActive==true) {
				try {
					if(echoBlueprint != null) {
						System.out.print("Blueprint: ");
						echoBlueprint.PrintEcho();
					}
					Thread.sleep(5000);
				} catch (Exception e) {
					System.out.println("Exception Triggered: "+e.getMessage());
				}
			}
		}
		
		public void stopThread() {
			isActive = false;
		}
	}
}
