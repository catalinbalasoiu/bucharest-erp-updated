package com.bucharest.ag.service;

import java.util.List;

import com.bucharest.ag.forms.DocumentForm;

public interface DocumentJpaService {
	
	 List<DocumentForm> listPartnerDocs(int partnerId);
	 
	 void deleteDocumentById(int docId);

}
