package com.example.drones.persistence.model;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.example.drones.enm.DroneModel;
import com.example.drones.enm.DroneState;
import com.example.drones.mapping.deserializer.PercentageDeserializer;
import com.example.drones.mapping.serializer.PercentageSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;
	
	@Column(nullable = false, unique = true)
	@Length(max = 100)
	private String serial;
	@Column(nullable = false)
	private DroneModel model;
	@Column
	@Max(value = 500, message = "MAX_WEIGHT_LIMIT")
	@Min(value = 0, message = "MIN_WEIGHT_LIMIT")
	private Integer weightLimit;
	@Column
	@Max(value = 1, message = "MAX_BATTARY_CAPACITY")
	@Min(value = 0, message = "MIN_BATTARY_CAPACITY")
	@JsonSerialize(using = PercentageSerializer.class)
	@JsonDeserialize(using = PercentageDeserializer.class)
	private Float battaryCapacity;
	@Column(nullable = false)
	private DroneState state;
	
	@JsonIgnore
	@OneToMany(mappedBy = "drone", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LoadedItem> items;
	
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

	public Integer getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Integer weight) {
		this.weightLimit = weight;
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

	public List<LoadedItem> getItems() {
		return items;
	}

	public void setItems(List<LoadedItem> items) {
		this.items = items;
	}
}
