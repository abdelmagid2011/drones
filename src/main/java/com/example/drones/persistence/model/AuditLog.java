package com.example.drones.persistence.model;

import java.util.Date;

import com.example.drones.enm.AuditLogAction;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	private Date auditDate = new Date();
	
	@Column
	private AuditLogAction auditAction;
	
	@Column
	private Long objectId;
	
	@Column
	private String objectType;
	
	@Column
	private String content;

	public AuditLog() {
		
	}
	
	public AuditLog(AuditLogAction auditAction, Long objectId, String objectType, String value) {
		super();
		this.auditAction = auditAction;
		this.objectId = objectId;
		this.objectType = objectType;
		this.content = value;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuditLogAction getAuditAction() {
		return auditAction;
	}

	public void setAuditAction(AuditLogAction auditAction) {
		this.auditAction = auditAction;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
