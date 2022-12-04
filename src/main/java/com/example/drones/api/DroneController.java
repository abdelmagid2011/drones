package com.example.drones.api;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.drones.exceptions.DroneInvalidCreationParamsException;
import com.example.drones.exceptions.DroneMismatchException;
import com.example.drones.exceptions.DroneNotFoundException;
import com.example.drones.persistence.model.Drone;
import com.example.drones.persistence.repo.DroneRepository;

@RestController
@RequestMapping("/api/drones")
public class DroneController {
	
	@Autowired
	private DroneRepository droneRepository;
	
	@GetMapping
	public Iterable<Drone> findAll() {
		return droneRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Drone findOne(@PathVariable Long id) {
		return droneRepository.findById(id).orElseThrow(DroneNotFoundException::new);
	}
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Drone create(@RequestBody Drone drone) {
		if(drone.getId() != null)
			throw new DroneInvalidCreationParamsException();
		try {
			return droneRepository.save(drone);
		}catch (Exception e) {
			throw new DroneInvalidCreationParamsException();
		}
	}
}
