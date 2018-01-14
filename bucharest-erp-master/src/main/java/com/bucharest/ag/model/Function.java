package com.bucharest.ag.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FUNCT")
public class Function {

	@Id
	@GeneratedValue
	@Column(name = "FUNC_ID")
	private int functionId;

	@Column(name = "OWNER", columnDefinition = "nvarchar")
	private String owner;

	@Column(name = "TYPE", columnDefinition = "nvarchar")
	private String type;

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}