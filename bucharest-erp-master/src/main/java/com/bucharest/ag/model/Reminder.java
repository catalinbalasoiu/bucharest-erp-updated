package com.bucharest.ag.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RMNDR")
public class Reminder implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "RMNDR_ID")
	private int reminderId;

	@Column(name = "RMNDR_DT")
	private Date reminderDate;

	@Column(name = "STATUS")
	private int reminderStatus;

	@Column(name = "TYPE", columnDefinition = "nvarchar")
	private String reminderType;

	@Column(name = "CREAT_BY", columnDefinition = "nvarchar")
	private String createdBy;

	@Column(name = "CREAT_DT")
	private Date createdDate;

	@Column(name = "UPDT_BY", columnDefinition = "nvarchar")
	private String updatedBy;

	@Column(name = "UPDT_DT")
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNC_ID", referencedColumnName = "FUNC_ID")
	private Function function;

	public void setFunctionId(Function function) {
		this.function = function;
	}

	public Function getFunction() {
		return function;
	}

	public int getReminderId() {
		return reminderId;
	}

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public int getReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(int reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
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

}
