package com.example.drones.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.drones.enm.AuditLogAction;
import com.example.drones.persistence.model.AuditLog;
import com.example.drones.persistence.repo.AuditLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuditLogServiceImpl implements AuditLogService {

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private AuditLogRepository auditLogRepo;
	
	@Override
	public void addAuditLog(AuditLogAction action, Long id, String typeName, String value) {
		auditLogRepo.save(new AuditLog(action, id, typeName, value));
	}

	@Override
	public void addAuditLog(AuditLogAction action, Long id, String typeName, Object value) {
		try {
			this.addAuditLog(action, id, typeName, mapper.writeValueAsString(value));
		} catch (JsonProcessingException e) {
			this.addAuditLog(AuditLogAction.SerializeException, id, typeName, e.getMessage());
		}
	}

	@Override
	public Iterable<AuditLog> findAll() {
		return auditLogRepo.findAll();
	}

	@Override
	public List<AuditLog> findAllByAction(String action) {
		return auditLogRepo.findAllByAuditAction(action);
	}

	@Override
	public List<AuditLog> findAllById(Long id) {
		return auditLogRepo.findAllByObjectId(id);
	}

	@Override
	public List<AuditLog> findAllByType(String type) {
		return auditLogRepo.findAllByObjectType(type);
	}

}
