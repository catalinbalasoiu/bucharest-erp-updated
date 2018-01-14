package com.bucharest.ag.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.context.ApplicationContextHolder;
import com.bucharest.ag.dao.DocumentDao;
import com.bucharest.ag.dao.DocumentDaoImpl;
import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Reminder;

@Service
@CacheConfig(cacheNames = "documents")
public class DocumentServiceImpl implements DocumentService {
	private ApplicationContext applicationContext = ApplicationContextHolder
			.getContext();

	@Autowired
	private DocumentDao documentDao;

	@Override
	@Cacheable
	public List<DocumentForm> listPartnerDocs(int partnerId) {
		return documentDao.listPartnerDocs(partnerId);
	}

	@Override
	public void deleteDocumentById(int docId, int reminderId) {
		documentDao.deleteDocumentById(docId, reminderId);
	}

	@Override
	public void addOrEditDocument(Document document) {
		documentDao.addOrEditDocument(document);

	}

	@Override
	public Document getDocumentById(int docId) {
		return documentDao.getDocumentById(docId);
	}

	@Override
	public void addOrEditReminder(Reminder reminder) {
		documentDao.addOrEditReminder(reminder);

	}

	@Override
	public int checkIfMasterExists(String master) {
		return documentDao.checkIfMasterExists(master);
	}
	
	@Override
	public int checkIfJCodeExists(String jCode) {
		return documentDao.checkIfJCodeExists(jCode);
	}
	
	@Override
	public int checkIfVatNumberExists(String vatNumber) {
		return documentDao.checkIfVatNumberExists(vatNumber);
	}


	@Override
	public Reminder getReminderById(int reminderId) {
		return documentDao.getReminderById(reminderId);
	}

	@Override
	public Document getDocByReminder(Reminder reminder) {
		SessionFactory sessionFactory = (SessionFactory) applicationContext
				.getBean("sessionFactory");
		DocumentDao documentDao = new DocumentDaoImpl(sessionFactory);

		return documentDao.getDocByReminder(reminder);
	}

}
