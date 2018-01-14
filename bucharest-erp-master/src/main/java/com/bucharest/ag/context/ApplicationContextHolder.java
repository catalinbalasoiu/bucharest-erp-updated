package com.bucharest.ag.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Helper class for instantiating beans outside of Spring context
 * An example are classes instantiated from Quartz jobs.
 *@author Sabin Antohe
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware  {

	
	private static ApplicationContext context;
	
	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
		
	}

}