package com.example.drones.persistence.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.example.drones.enm.DroneModel;
import com.example.drones.enm.DroneState;
import com.example.drones.mapping.deserializer.PercentageDeserializer;
import com.example.drones.mapping.serializer.PercentageSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	@Length(max = 100)
	private String serial;
	@Column(nullable = false)
	private DroneModel model;
	@Column
	@Max(value = 500, message = "MAX_WEIGHT")
	@Min(value = 0, message = "MIN_WEIGHT")
	private Integer weight;
	@Column
	@Max(value = 1, message = "MAX_CAPACITY")
	@Min(value = 0, message = "MIN_CAPACITY")
	@JsonSerialize(using = PercentageSerializer.class)
	@JsonDeserialize(using = PercentageDeserializer.class)
	private Float battaryCapacity;
	@Column(nullable = false)
	private DroneState state;
	
	public Drone() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public DroneModel getModel() {
		return model;
	}

	public void setModel(DroneModel model) {
		this.model = model;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Float getBattaryCapacity() {
		return battaryCapacity;
	}

	public void setBattaryCapacity(Float battaryCapacity) {
		this.battaryCapacity = battaryCapacity;
	}

	public DroneState getState() {
		return state;
	}

	public void setState(DroneState state) {
		this.state = state;
	}
}
