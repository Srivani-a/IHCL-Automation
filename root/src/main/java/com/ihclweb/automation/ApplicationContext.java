package com.ihclweb.automation;

import java.util.Objects;

import com.ihclweb.automation.provider.credential.ResourceProvider;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class ApplicationContext {

	private final ApplicationConfig config;
	private static ApplicationContext context = null;
	private final ResourceProvider resourceProvider;
	
	
	private ApplicationContext() {
		config = new ApplicationConfig();
		resourceProvider = ResourceProvider.instance();
	}
	
	public ResourceProvider getResourceProvider() {
		return resourceProvider;
	}

	public static ApplicationContext getContext(){
		if(Objects.isNull(context))
			context = new ApplicationContext();
		return context;
	}

	public ApplicationConfig getConfig() {
		return config;
	}
}
