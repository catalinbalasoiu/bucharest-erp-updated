package com.bucharest.ag.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
 
@Entity
@Table(name = "DOC")
public class Document {

	@Id
	@GeneratedValue
	@Column(name = "DOC_ID")
	private int docId;
	
	@Column(name = "PRTNR_ID")
	private int partnerId;
	
	@Column(name = "DOC_NAM", columnDefinition = "nvarchar")
    private String docName;
	
	@Column(name = "PATH", columnDefinition = "nvarchar")
    private String fileName;
	
	@Column(name = "MASTER", columnDefinition = "nvarchar")
    private String master;
	
	@Column(name = "DOC_NBR", columnDefinition = "nvarchar")
    private String docNumber;
	
	@Column(name = "EXP_DT", columnDefinition = "nchar")
    private String expDate;
	
	@Column(name = "TAGS", columnDefinition = "nvarchar")
    private String tags;
	
	@Column(name = "COMMENT", columnDefinition = "nvarchar")
    private String comment;
	
	@Column(name="CREAT_BY", columnDefinition = "nvarchar")
	private String createdBy;
	
	@Column(name="CREAT_DT")
	private Date createdDate;
	
	@Column(name="UPDT_BY", columnDefinition = "nvarchar")
	private String updatedBy;
	
	@Column(name="UPDT_DT")
	private Date updatedDate;

	@OneToOne(targetEntity = Reminder.class, cascade=CascadeType.REMOVE, optional=true)
	@JoinColumn(name="RMNDR_ID", referencedColumnName =  "RMNDR_ID")
    private Reminder reminder;
    
	public void setReminder(Reminder reminder) {
		this.reminder=reminder;
	}
	
    public Reminder getReminder() {
          return reminder;
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

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/*@Override
	public String toString() {
		return "Document [docId=" + docId + ", partnerId=" + partnerId
				+ ", docName=" + docName + ", fileName=" + fileName
				+ ", master=" + master + ", docNumber=" + docNumber
				+ ", expDate=" + expDate + ", tags=" + tags + ", comment="
				+ comment + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", reminderId=" + reminder + "]";
	}*/
	
	
}