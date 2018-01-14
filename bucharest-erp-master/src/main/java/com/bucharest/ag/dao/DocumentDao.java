package com.bucharest.ag.dao;

import java.util.List;

import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Reminder;

public interface DocumentDao {
	public List<DocumentForm> listPartnerDocs(int partnerId);
	public void deleteDocumentById(int docId, int reminderId);
	public void addOrEditDocument(Document document);
	public Document getDocumentById(int docId);
	void addOrEditReminder(Reminder reminder);
	public int checkIfMasterExists(String master);
	public int checkIfJCodeExists(String jCode);
	public int checkIfVatNumberExists(String vatNumber);
	public Reminder getReminderById (int reminderId);
	public Document getDocByReminder(Reminder reminder);
}
