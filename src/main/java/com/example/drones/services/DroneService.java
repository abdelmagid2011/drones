package com.example.drones.services;


import java.util.List;

import com.example.drones.dto.BattaryLevelDto;
import com.example.drones.dto.StateDto;
import com.example.drones.exceptions.InvalidDroneStateException;
import com.example.drones.exceptions.DroneLowBattaryException;
import com.example.drones.exceptions.DroneOverloadedException;
import com.example.drones.exceptions.InvaliedLoadedItemsException;
import com.example.drones.persistence.model.Drone;
import com.example.drones.persistence.model.LoadedItem;

public interface DroneService {

	public Iterable<Drone> findAll();

	public Drone findById(Long id);

	public Drone save(Drone drone);

	public Drone create(Drone drone);

	public StateDto loadDrone(String serial, List<LoadedItem> loadedItem) throws InvalidDroneStateException, DroneLowBattaryException, DroneOverloadedException, InvaliedLoadedItemsException;

	public StateDto droneState(String serial);

	public List<LoadedItem> getDroneLoadedItems(String serial);

	public BattaryLevelDto droneBattaryLevel(String serial);

	public List<Drone> getLoadedDrones();

	public List<Drone> getAvailableDrones();

	public StateDto droneFinishLoading(String serial) throws InvalidDroneStateException;

	public StateDto droneStartDelievering(String serial) throws InvalidDroneStateException;

	public StateDto droneItemDelievered(String serial) throws InvalidDroneStateException;

	public StateDto droneBack(String serial) throws InvalidDroneStateException;

	public void checkBattaryLevels();

}
