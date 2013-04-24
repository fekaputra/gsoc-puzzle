package me.juang.hello.server;

import me.juang.hello.api.EchoService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class EchoServiceActivator implements BundleActivator {
	private ServiceRegistration<EchoService> service;

	public void start(BundleContext context) throws Exception {
        System.out.println("Starting the server bundle");
		service = context.registerService(EchoService.class, new EchoServiceImpl(), null);
	}

	public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping the server bundle");
        service.unregister();
	}
	
}
