package com.example.drones.services;

import java.util.List;

import com.example.drones.enm.AuditLogAction;
import com.example.drones.persistence.model.AuditLog;

public interface AuditLogService {

	public void addAuditLog(AuditLogAction action, Long id, String typeName, String value);
	
	public void addAuditLog(AuditLogAction action, Long id, String typeName, Object value);

	public Iterable<AuditLog> findAll();

	public List<AuditLog> findAllByAction(String action);

	public List<AuditLog> findAllById(Long id);

	public List<AuditLog> findAllByType(String type);

}
