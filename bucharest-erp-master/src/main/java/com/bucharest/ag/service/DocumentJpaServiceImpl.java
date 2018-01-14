package com.bucharest.ag.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.forms.DocumentForm;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.repository.DocumentRepository;

@Service
@Transactional
public class DocumentJpaServiceImpl implements DocumentJpaService {

	@Autowired 
	private DocumentRepository documentRepository;
	
	@Override
	public List<DocumentForm> listPartnerDocs(int partnerId) {
		List<Document> masterAndChildrenList = documentRepository.getMastersAndChildren(partnerId);
		List<Document> masterNoChildrenList = documentRepository.getMastersNoChildren(partnerId);
		
		masterAndChildrenList.addAll(masterNoChildrenList);
		List<Document> allDocs = masterAndChildrenList;
		
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
		
		return documentFormList;
	}

	@Override
	public void deleteDocumentById(int docId) {
		documentRepository.deleteByDocId(docId);
		
	}

}
