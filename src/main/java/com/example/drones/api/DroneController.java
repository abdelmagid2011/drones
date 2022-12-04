package com.example.drones.api;


import java.util.List;

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

import com.example.drones.dto.BattaryLevelDto;
import com.example.drones.dto.StateDto;
import com.example.drones.exceptions.InvalidDroneStateException;
import com.example.drones.exceptions.DroneLowBattaryException;
import com.example.drones.exceptions.DroneOverloadedException;
import com.example.drones.exceptions.InvaliedLoadedItemsException;
import com.example.drones.persistence.model.Drone;
import com.example.drones.persistence.model.LoadedItem;
import com.example.drones.services.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {
	
	@Autowired
	private DroneService droneService;
	
	@GetMapping
	public Iterable<Drone> findAll() {
		return droneService.findAll();
	}
	
	@GetMapping("/{id}")
	public Drone findOne(@PathVariable Long id) {
		return droneService.findById(id);
	}
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Drone create(@RequestBody Drone drone) {
		return droneService.create(drone);
	}

	@PostMapping("/load/{serial}")
	public StateDto loadDrone(@PathVariable String serial,@RequestBody List<LoadedItem> loadedItems) throws InvalidDroneStateException, DroneLowBattaryException, DroneOverloadedException, InvaliedLoadedItemsException {
		
		return droneService.loadDrone(serial, loadedItems);
	}
	@GetMapping("/state/{serial}")
	public StateDto droneState(@PathVariable String serial) {
		return droneService.droneState(serial);
	}
	@GetMapping("/battary-level/{serial}")
	public BattaryLevelDto droneBattaryLevel(@PathVariable String serial) {
		return droneService.droneBattaryLevel(serial);
	}

	@GetMapping("/loaded")
	public List<Drone> loadedDrones() {
		return droneService.getLoadedDrones();
	}
	

	@GetMapping("/available")
	public List<Drone> availableDrones() {
		return droneService.getAvailableDrones();
	}
	
	@GetMapping("/loaded-items/{serial}")
	public List<LoadedItem> droneLoadedItems(@PathVariable String serial) {
		return droneService.getDroneLoadedItems(serial);
	}
	
	@PutMapping("/finish-loading/{serial}")
	public StateDto finishLoading(@PathVariable String serial) throws InvalidDroneStateException {
		return droneService.droneFinishLoading(serial);
	}
	@PutMapping("/start-delievering/{serial}")
	public StateDto startDelievering(@PathVariable String serial) throws InvalidDroneStateException {
		return droneService.droneStartDelievering(serial);
	}
	@PutMapping("/items-delievered/{serial}")
	public StateDto delevered(@PathVariable String serial) throws InvalidDroneStateException {
		return droneService.droneItemDelievered(serial);
	}
	@PutMapping("/drone-back/{serial}")
	public StateDto droneBack(@PathVariable String serial) throws InvalidDroneStateException {
		return droneService.droneBack(serial);
	}
	
}
