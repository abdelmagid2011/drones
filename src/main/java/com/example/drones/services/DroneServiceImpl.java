package com.example.drones.services;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.drones.dto.BattaryLevelDto;
import com.example.drones.dto.StateDto;
import com.example.drones.enm.AuditLogAction;
import com.example.drones.enm.DroneState;
import com.example.drones.exceptions.DroneInvalidCreationParamsException;
import com.example.drones.exceptions.InvalidDroneStateException;
import com.example.drones.exceptions.DroneLowBattaryException;
import com.example.drones.exceptions.DroneNotFoundException;
import com.example.drones.exceptions.DroneOverloadedException;
import com.example.drones.exceptions.InvaliedLoadedItemsException;
import com.example.drones.persistence.model.Drone;
import com.example.drones.persistence.model.LoadedItem;
import com.example.drones.persistence.repo.DroneRepository;

@Service
public class DroneServiceImpl implements DroneService {
	
	@Autowired
	private DroneRepository droneRepo;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@Override
	public Iterable<Drone> findAll() {
		return droneRepo.findAll();
	}

	@Override
	public Drone findById(Long id) {
		return droneRepo.findById(id).orElseThrow(DroneNotFoundException::new);
	}

	@Override
	public Drone save(Drone drone) {
		if(drone.getId() != null)
			throw new DroneInvalidCreationParamsException();
		try {
			drone = droneRepo.save(drone);
			auditLogService.addAuditLog(AuditLogAction.SaveDrone, drone.getId(), Drone.class.getName(), drone);
			return drone;
		}catch (Exception e) {
			throw new DroneInvalidCreationParamsException();
		}
	}

	@Override
	public Drone create(Drone drone) {
		if(drone.getId() != null)
			throw new DroneInvalidCreationParamsException();
		try {
			// set default creation state
			drone.setState(DroneState.IDLE);
			drone = droneRepo.save(drone);
			auditLogService.addAuditLog(AuditLogAction.CreateDrone, drone.getId(), Drone.class.getName(), drone);
			return drone;
		}catch (Exception e) {
			throw new DroneInvalidCreationParamsException();
		}
	}

	@Override
	public StateDto loadDrone(String serial, List<LoadedItem> loadedItems) throws InvalidDroneStateException, DroneLowBattaryException, DroneOverloadedException, InvaliedLoadedItemsException {
		Drone drone = droneRepo.findBySerial(serial).orElseThrow(DroneNotFoundException::new);
		
		if(drone.getState() == DroneState.IDLE || drone.getState() == DroneState.LOADING) {
			if(loadedItems == null || loadedItems.isEmpty())
				throw new InvaliedLoadedItemsException(); 
			if(drone.getBattaryCapacity() < .25)
				throw new DroneLowBattaryException();
			boolean willOverLoad = checkIfOverload(drone, loadedItems);
			if(willOverLoad)
				throw new DroneOverloadedException();
			
			drone.setState(DroneState.LOADING);
			for (LoadedItem loadedItem : loadedItems) {
				loadedItem.setDrone(drone);
			}
			if(drone.getItems() == null)
				drone.setItems(loadedItems);
			else
				drone.getItems().addAll(loadedItems);
			
			droneRepo.save(drone);
			auditLogService.addAuditLog(AuditLogAction.loadDrone, drone.getId(), Drone.class.getName(), loadedItems);
		}else {
			throw new InvalidDroneStateException();
		}
		
		return new StateDto(drone.getState().toString());
	}

	private boolean checkIfOverload(Drone drone, List<LoadedItem> loadedItems) {
		Integer loadedWeight = 0;
		if(drone.getItems() != null && !drone.getItems().isEmpty())
			loadedWeight = drone.getItems().stream().collect(Collectors.summingInt(item -> item.getWeight()));
		Integer newWeight = loadedItems.stream().collect(Collectors.summingInt(item -> item.getWeight()));
		
		return (newWeight + loadedWeight) > drone.getWeightLimit();
	}

	@Override
	public StateDto droneState(String serial) {
		Drone drone = droneRepo.findBySerial(serial).orElseThrow(DroneNotFoundException::new);
		return new StateDto(drone.getState().toString());
	}

	@Override
	public List<LoadedItem> getDroneLoadedItems(String serial) {
		Drone drone = droneRepo.findBySerial(serial).orElseThrow(DroneNotFoundException::new);
		return drone.getItems();
	}

	@Override
	public BattaryLevelDto droneBattaryLevel(String serial) {
		Drone drone = droneRepo.findBySerial(serial).orElseThrow(DroneNotFoundException::new);
		return new BattaryLevelDto(NumberFormat.getPercentInstance().format(drone.getBattaryCapacity()));
	}

	@Override
	public List<Drone> getLoadedDrones() {
		return droneRepo.findAllByState(DroneState.LOADED);
	}

	@Override
	public List<Drone> getAvailableDrones() {
		return droneRepo.findAllBybattaryCapacityGreaterThanEqualAndStateIn(.25f, Arrays.asList(DroneState.IDLE, DroneState.LOADING));
	}

	@Override
	public StateDto droneFinishLoading(String serial) throws InvalidDroneStateException {
		return updateDroneState(serial, DroneState.LOADING, DroneState.LOADED, false);
	}

	private StateDto updateDroneState(String serial, DroneState oldState, DroneState newState, Boolean removeItems) throws InvalidDroneStateException {
		Drone drone = droneRepo.findBySerial(serial).orElseThrow(DroneNotFoundException::new);
		if(drone.getState() != oldState)
			throw new InvalidDroneStateException();
		else {
			drone.setState(newState);
			if(removeItems)
				drone.getItems().clear();
			droneRepo.save(drone);
			auditLogService.addAuditLog(AuditLogAction.StateChange, drone.getId(), Drone.class.getName(), Arrays.asList(oldState, newState, removeItems));
		}
		return new StateDto(drone.getState().toString());
	}

	@Override
	public StateDto droneStartDelievering(String serial) throws InvalidDroneStateException {
		return updateDroneState(serial, DroneState.LOADED, DroneState.DELIVERING, false);
	}

	@Override
	public StateDto droneItemDelievered(String serial) throws InvalidDroneStateException {
		return updateDroneState(serial, DroneState.DELIVERING, DroneState.DELIVERED, true);
	}

	@Override
	public StateDto droneBack(String serial) throws InvalidDroneStateException {
		return updateDroneState(serial, DroneState.DELIVERED, DroneState.IDLE, false);
	}

	@Override
	public void checkBattaryLevels() {
		Iterable<Drone> drones = droneRepo.findAll();
		for (Drone drone : drones) {
			auditLogService.addAuditLog(AuditLogAction.BattaryCheck, drone.getId(), Drone.class.getName(), NumberFormat.getPercentInstance().format(drone.getBattaryCapacity()));
		}
	}
}
