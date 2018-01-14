package com.bucharest.ag.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Partner;
import com.bucharest.ag.model.Reminder;

@Repository
@Transactional
public class DocumentDaoImpl implements DocumentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/***
	 * When the class is instantiated inside the Spring Context
	 */
	public DocumentDaoImpl() {
	}
	
	/**
	 * When the class us instantiated outside the Spring Context
	 * @param sessionFactory
	 */
	public DocumentDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentForm> listPartnerDocs(int partnerId) {
		Session session = sessionFactory.openSession();
		String getMastersAndChildren = "FROM Document as d1 WHERE d1.partnerId= :partnerId and d1.master not in (select master from Document group by master having count(docNumber) <= 1) ORDER BY master, docNumber, docName";
	
		String getMastersNoChildren = "FROM Document as d1 WHERE d1.partnerId= :partnerId and d1.master not in (select master from Document group by master having count(docNumber) > 1) ORDER BY master, docNumber, docName";
		
		Query query1 = session.createQuery(getMastersAndChildren);
		Query query2 = session.createQuery(getMastersNoChildren);
		
		query1.setParameter("partnerId", partnerId);
		query2.setParameter("partnerId", partnerId);
		
		List<Document> allDocs = (List<Document>) query1.list();
		allDocs.addAll((List<Document>) query2.list());
		
		List<DocumentForm> documentFormList = new ArrayList<DocumentForm>();
		for (Document document : allDocs) {
			DocumentForm doc = new DocumentForm();
			doc.setDocId(document.getDocId());
			doc.setDocName(document.getDocName());
			doc.setDocNumber(document.getDocNumber());
			doc.setMaster(document.getMaster());
			doc.setComment(document.getComment());
			doc.setExpDate(document.getExpDate());
			doc.setFileName(document.getFileName());
			doc.setPartnerId(document.getPartnerId());
			
			Date date = new Date();
			date.setTime(document.getCreatedDate().getTime());
			String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
			doc.setCreatedDate(formattedDate);
			if(document.getReminder() != null) {
				doc.setReminderId(document.getReminder().getReminderId());
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
				String strDate = dateFormat.format(document.getReminder().getReminderDate());  
				doc.setReminderDate(strDate);
			} else {
				doc.setReminderDate("");
			}
			doc.setTags(document.getTags());
			documentFormList.add(doc);
		}
		session.close();
		return documentFormList;
	}

	@Override
	public void deleteDocumentById(int docId, int reminderId) {
		Session session = sessionFactory.openSession();
		Query queryDeleteReminder = session
				.createQuery("DELETE FROM Reminder WHERE reminderId = :reminderId");
		Query queryDeleteDoc = session
				.createQuery("DELETE FROM Document WHERE docId = :docId");
		queryDeleteReminder.setParameter("reminderId", reminderId);
		queryDeleteDoc.setParameter("docId", docId);
		queryDeleteReminder.executeUpdate();
		queryDeleteDoc.executeUpdate();
		session.close();
		
	}

	@Override
	public void addOrEditDocument(Document document) {
		Session session = sessionFactory.openSession();
	    session.saveOrUpdate(document);
	    session.flush();
		session.close();
	}
	
	@Override
	public void addOrEditReminder(Reminder reminder) {
		Session session = sessionFactory.openSession();
	    session.saveOrUpdate(reminder);
	    session.flush();
		session.close();
	}

	
	@Override
	public Document getDocumentById(int docId) {
		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("FROM Document WHERE docId = :docId");
		query.setParameter("docId", docId);
	
		Document document = (Document) query.uniqueResult();
		session.close();
		return document;
	}
	
	@Override
	public Reminder getReminderById (int reminderId) {
		Session session = sessionFactory.openSession();
		Query query = session
				.createQuery("FROM Reminder WHERE reminderId = :reminderId");
		query.setParameter("reminderId", reminderId);
	
		Reminder reminder = (Reminder) query.uniqueResult();
		session.close();
		return reminder;
	}
	
	@Override
	public int checkIfMasterExists(String master) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Document WHERE master = :master");
		query.setParameter("master", master);
		List<Document> documents = (List<Document>) query.list();
		int a  = documents.size();
		session.close();
		return a;
	}
	
	@Override
	public int checkIfJCodeExists(String jCode) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Partner WHERE jCode = :jCode");
		query.setParameter("jCode", jCode);
		List<Partner> partners = (List<Partner>) query.list();
		int a  = partners.size();
		session.close();
		return a;
	}
	
	@Override
	public int checkIfVatNumberExists(String vatNumber) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Partner WHERE vatNumber = :vatNumber");
		query.setParameter("vatNumber", vatNumber);
		List<Partner> partners = (List<Partner>) query.list();
		int a  = partners.size();
		session.close();
		return a;
	}

	@Override
	public Document getDocByReminder(Reminder reminder) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Document WHERE reminder = :reminder");
		query.setParameter("reminder", reminder);
		Document document = (Document) query.uniqueResult();
		return document;
	}
	
}
