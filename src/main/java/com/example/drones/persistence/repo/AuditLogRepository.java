package com.example.drones.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.drones.persistence.model.AuditLog;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long>{

	public List<AuditLog> findAllByAuditAction(String action);

	public List<AuditLog> findAllByObjectType(String type);

	public List<AuditLog> findAllByObjectId(Long id);

}
