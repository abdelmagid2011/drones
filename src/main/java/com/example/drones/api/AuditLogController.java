package com.example.drones.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.drones.persistence.model.AuditLog;
import com.example.drones.services.AuditLogService;

@RestController
@RequestMapping("/api/audit-log")
public class AuditLogController {

	@Autowired
	private AuditLogService auditLogService;
	
	@GetMapping
	public Iterable<AuditLog> findAll(){
		return auditLogService.findAll();
	}
	@GetMapping("/action/{action}")
	public List<AuditLog> findAllByAction(@PathVariable String action){
		return auditLogService.findAllByAction(action);
	}
	@GetMapping("/id/{id}")
	public List<AuditLog> findAllById(@PathVariable Long id){
		return auditLogService.findAllById(id);
	}
	@GetMapping("/type/{id}")
	public List<AuditLog> findAllByType(@PathVariable String type){
		return auditLogService.findAllByType(type);
	}
	
}
