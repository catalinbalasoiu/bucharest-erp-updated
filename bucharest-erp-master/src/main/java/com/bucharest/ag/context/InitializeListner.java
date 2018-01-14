package com.bucharest.ag.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;

import com.bucharest.ag.jobs.Scheduler;

/**
 * This class is used for initialization on start up and clean up on shutdown
 * 
 *@author Sabin Antohe
 */

public class InitializeListner implements ServletContextListener {

	/** 
	 * This method is invoked when the server starts.
	 * Use this method to initialize 
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Scheduler.scheduleReminderJob();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is invoked when the server is shutting down
	 * Use this method to clean up
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	
	}
}
