package com.example.drones.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.drones.config.SystemParameters;
import com.example.drones.services.DroneService;

@Configuration
@EnableScheduling
public class BattaryDecreaseJob extends Job{

	@Autowired
	private DroneService droneService;
	
	
	@Scheduled(fixedDelayString = "${battary.check.interval}")
	protected void BattaryDecreaseJob() {
		droneService.checkBattaryLevels();
	}
}
