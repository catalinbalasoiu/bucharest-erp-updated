package com.bucharest.ag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Reminder;

public interface DocumentRepository extends Repository<Document, Integer> {
	
	@Query("FROM Document as d1 WHERE d1.partnerId= ?1 and d1.master not in (select master from Document group by master having count(docNumber) <= 1) ORDER BY master, docNumber, docName")
	List<Document> getMastersAndChildren(int partnerId);
	
	@Query("FROM Document as d1 WHERE d1.partnerId= ?1 and d1.master not in (select master from Document group by master having count(docNumber) > 1) ORDER BY master, docNumber, docName")
	List<Document> getMastersNoChildren(int partnerId);

	void deleteByDocId(int docId);

	/*public void addOrEditDocument(Document document);

	public void addOrEditReminder(Reminder reminder);

	public Document getDocumentByDocId(int docId);

	public int checkIfMasterExists(String master);
	
	public int checkIfJCodeExists(String jCode);
	
	public int checkIfVatNumberExists(String vatNumber);

	public Reminder getReminderByDocId(int reminderId);

	public Document getDocByReminder(Reminder reminder);*/
}
