package com.bucharest.ag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.model.Partner;
import com.bucharest.ag.repository.PartnerRepository;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;
	
	@Override
	public List<Partner> findAllByPartnerType(String type) {
		return partnerRepository.findAllByPartnerType(type);
	}

	@Override
	public void addPartner(Partner partner) {
		partnerRepository.save(partner);
	}

	@Override
	public void deletePartnerById(int id) {
		partnerRepository.deletePartnerByPartnerId(id);		
	}

	@Override
	public Partner findPartnerById(int id) {
		return partnerRepository.findByPartnerId(id);
	}

	@Override
	public void updatePartner(Partner partner) {
		partnerRepository.save(partner);
	}

}
