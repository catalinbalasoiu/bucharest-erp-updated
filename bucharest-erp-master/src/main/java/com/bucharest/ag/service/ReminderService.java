package com.bucharest.ag.service;

import java.util.List;

import com.bucharest.ag.model.Reminder;

public interface ReminderService {
	public void addOrEditReminder(Reminder reminder);

	public List<Reminder> getReminders(String todayDate);

	public Reminder getReminderById(int reminderId);
}
