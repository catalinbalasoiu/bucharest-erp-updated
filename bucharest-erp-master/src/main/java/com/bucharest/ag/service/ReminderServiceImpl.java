package com.bucharest.ag.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.context.ApplicationContextHolder;
import com.bucharest.ag.dao.ReminderDao;
import com.bucharest.ag.dao.ReminderDaoImpl;
import com.bucharest.ag.model.Reminder;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {
	private ApplicationContext applicationContext = ApplicationContextHolder
			.getContext();

	@Autowired
	private ReminderDao reminderDao;

	@Override
	public void addOrEditReminder(Reminder reminder) {
		reminderDao.addOrEditReminder(reminder);

	}

	@Override
	public Reminder getReminderById(int reminderId) {
		return reminderDao.getReminderById(reminderId);
	}

	/**
	 * This method is called by classes outside of Spring context. Therefore, we
	 * can't use @Autowired to instantiate classes.
	 * 
	 * @param Date
	 *            - Is the current date
	 */
	@Override
	public List<Reminder> getReminders(String todayDate) {
		SessionFactory sessionFactory = (SessionFactory) applicationContext
				.getBean("sessionFactory");
		ReminderDao reminderDao = new ReminderDaoImpl(sessionFactory);

		return reminderDao.getReminders(todayDate);
	}
}
