package com.bucharest.ag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.bucharest.ag.model.Partner;

public interface PartnerRepository extends Repository<Partner, Integer> {
	
	@Query("FROM Partner WHERE partnerType like %?1")
	List<Partner> findAllByPartnerType(String type);
	
	void save(Partner partner);
	
	void deletePartnerByPartnerId(int id);
	
	Partner findByPartnerId(int id);
}
