package com.example.rently.services.impl;

import java.util.List;
// import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rently.models.Vehicle;
import com.example.rently.repository.VehicleRepository;
import com.example.rently.services.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService{

	private VehicleRepository vehicleRepository;
	
	public VehicleServiceImpl(VehicleRepository vehicleRepository) {
		super();
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public Vehicle addVehicle(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	@Override
	public Vehicle getVehicleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle updateVehicle(Vehicle vehicle, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVehicle(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vehicle rentVehicle(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
