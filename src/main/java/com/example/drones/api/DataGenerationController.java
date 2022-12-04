package com.example.drones.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.drones.enm.DroneModel;
import com.example.drones.persistence.model.Drone;

@RestController
@RequestMapping("/api/drones-generator")
public class DataGenerationController {

	
	@Autowired
	private DroneController controller;
	
	@GetMapping("/{num}/{baseSerial}")
	public List<Drone> generateDrones(@PathVariable Integer num, @PathVariable String baseSerial) {
		List<Drone> drones = new ArrayList<Drone>();
		DroneModel[] models = DroneModel.values();
		for (int i = 0; i < num; i++) {
			Drone drone = new Drone();
			drone.setSerial(baseSerial + (i+101));
			drone.setBattaryCapacity(Math.round(Math.random()*100)/100f);
			drone.setWeightLimit(((i * 100) % 500) +100);
			drone.setModel(models[i % models.length]);
			controller.create(drone);
			drones.add(drone);
		}
		return drones;
	}
}
