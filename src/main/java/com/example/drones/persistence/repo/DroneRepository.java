package com.example.drones.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.drones.persistence.model.Drone;

public interface DroneRepository  extends CrudRepository<Drone, Long>{

}
