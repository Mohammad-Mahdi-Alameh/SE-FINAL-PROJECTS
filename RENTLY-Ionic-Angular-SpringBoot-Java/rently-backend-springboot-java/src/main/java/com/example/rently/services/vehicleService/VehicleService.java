package com.example.rently.services.vehicleService;

import java.util.List;

import com.example.rently.models.vehicle.Vehicle;

public interface VehicleService {
	Vehicle addVehicle(Vehicle vehicle);
	List<Vehicle> getAllVehicles();
	Vehicle getVehicleById(Long id);
	Vehicle updateVehicle(Vehicle vehicle, Long id);
	void deleteVehicle(Long id);
	Vehicle returnVehicle(Long id);
	Vehicle rentVehicle(Long id);
}
