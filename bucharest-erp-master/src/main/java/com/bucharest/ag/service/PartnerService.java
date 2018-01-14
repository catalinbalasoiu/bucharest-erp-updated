package com.bucharest.ag.service;

import java.util.List;

import com.bucharest.ag.model.Partner;

public interface PartnerService {
	List<Partner> findAllByPartnerType(String type);
	void addPartner(Partner partner);
	void deletePartnerById(int id);
	Partner findPartnerById(int id);
	void updatePartner(Partner partner);
}
