package com.bucharest.ag.service;

import java.util.List;

import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Reminder;

public interface DocumentService {
	public List<DocumentForm> listPartnerDocs(int partnerId);

	public void deleteDocumentById(int docId, int reminderId);

	public void addOrEditDocument(Document document);

	public void addOrEditReminder(Reminder reminder);

	public Document getDocumentById(int docId);

	public int checkIfMasterExists(String master);
	
	public int checkIfJCodeExists(String jCode);
	
	public int checkIfVatNumberExists(String vatNumber);

	public Reminder getReminderById(int reminderId);

	public Document getDocByReminder(Reminder reminder);
}
