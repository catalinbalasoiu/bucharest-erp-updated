package com.bucharest.ag.model;

//import java.sql.Date;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRTNR")
public class Partner implements Serializable {
	
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue
	@Column(name = "PRTNR_ID")
	private int partnerId;
	
	@Column(name="NAM", columnDefinition = "nvarchar")
	private String name;
	
	@Column(name="CNTCT_PRSN", columnDefinition = "nvarchar")
	private String contactPerson;
	
	@Column(name="J_CD", columnDefinition = "nvarchar")
	private String jCode;
	
	@Column(name="VAT", columnDefinition = "nvarchar")
	private String vatNumber;
	
	@Column(name="PON", columnDefinition = "nvarchar")
	private String phoneNumber;
	
	@Column(name="TAGS", columnDefinition = "nvarchar")
	private String tags;
	
	@Column(name="ADDR", columnDefinition = "nvarchar")
	private String address;
	
	@Column(name="COMMENT", columnDefinition = "nvarchar")
	private String comment;
	
	@Column(name="CREAT_BY", columnDefinition = "nvarchar")
	private String createdBy;
	
	@Column(name="CREAT_DT")
	private Date createdDate;
	
	@Column(name="UPDT_BY", columnDefinition = "nvarchar")
	private String updatedBy;
	
	@Column(name="UPDT_DT")
	private Date updatedDate;
	
	@Column(name="PRTNR_TYPE", columnDefinition = "char")
	private String partnerType;
	
	public int getPartnerId() {
		return partnerId;
	}

	public void setClientId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getjCode() {
		return jCode;
	}

	public void setjCode(String jCode) {
		this.jCode = jCode;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
}
