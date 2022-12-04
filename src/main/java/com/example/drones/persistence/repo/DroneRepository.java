package com.example.drones.persistence.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.drones.enm.DroneState;
import com.example.drones.persistence.model.Drone;

public interface DroneRepository  extends CrudRepository<Drone, Long>{

	public Optional<Drone> findBySerial(String serial);

	public List<Drone> findAllByState(DroneState state);

	public List<Drone> findAllBybattaryCapacityGreaterThanEqualAndStateIn(float f, List<DroneState> statusList);

}
