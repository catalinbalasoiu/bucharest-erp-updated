package com.bucharest.ag.dao;

import java.util.List;

import com.bucharest.ag.model.Reminder;

public interface ReminderDao {
	public void addOrEditReminder(Reminder reminder);
	public List<Reminder> getReminders(String todayDate);
	public Reminder getReminderById(int reminderId);
}
