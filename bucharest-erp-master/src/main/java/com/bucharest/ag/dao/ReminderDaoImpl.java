package com.bucharest.ag.dao;

import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.model.Reminder;

@Repository
@Transactional
public class ReminderDaoImpl implements ReminderDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	/***
	 * When the class is instantiated inside the Spring Context
	 */
	public ReminderDaoImpl() {
	}
	
	/**
	 * When the class us instantiated outside the Spring Context
	 * @param sessionFactory
	 */
	public ReminderDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addOrEditReminder(Reminder reminder) {
		Session session = sessionFactory.openSession();
	    session.saveOrUpdate(reminder);
	    session.flush();
		session.close();
	}
	
	@Override
	public Reminder getReminderById(int reminderId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("FROM Reminder WHERE reminderId = :reminderId");
		query.setParameter("reminderId", reminderId);
	
		Reminder reminder = (Reminder) query.uniqueResult();
		session.close();
		return reminder;
		
	}
	
	/**
	 * Retrieves all the reminders for this date
	 * 
	 * @param todayDate
	 */
	@Override
	public List<Reminder> getReminders(String todayDate) {
		Session session = null;
		UserTransaction tx = null;
		List<Reminder> results = null;
		
		try {
			tx = (UserTransaction) new InitialContext().lookup("java:jboss/UserTransaction");
			int txStatus = tx.getStatus();
			if (txStatus == Status.STATUS_MARKED_ROLLBACK || txStatus == Status.STATUS_ROLLEDBACK) {
				tx.rollback();
			}
			tx.begin();
			session = sessionFactory.getCurrentSession();
			Criteria cr =  session.createCriteria(Reminder.class);
			results = cr.list();
			for (Iterator<Reminder> iterator = results.iterator(); iterator.hasNext();) {
				Reminder reminder = iterator.next();
				
				if (!reminder.getReminderDate().toString().contains(todayDate)) {
					iterator.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
}
