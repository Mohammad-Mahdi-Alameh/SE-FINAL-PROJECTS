package com.example.rently.services;

import java.util.List;

import com.example.rently.models.Vehicle;

public interface VehicleService {
	Vehicle saveVehicle(Vehicle vehicle);
	List<Vehicle> getAllVehicles();
	Vehicle getVehicleById(Long id);
	Vehicle updateVehicle(Vehicle vehicle, Long id);
	void deleteVehicle(Long id);
	Vehicle rentVehicle(Long id);
}
