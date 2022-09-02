package com.example.rently.services.impl;

import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rently.exceptions.ResourceNotFoundException;
import com.example.rently.models.Vehicle;
import com.example.rently.repository.VehicleRepository;
import com.example.rently.services.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository vehicleRepository;

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
//		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
//		if(vehicle.isPresent()) {
//			return vehicle.get();
//		}else {
//			throw new ResourceNotFoundException("Vehicle", "Id", id);
//		}
		return vehicleRepository.findById(id).orElseThrow(() -> 
						new ResourceNotFoundException("Vehicle", "Id", id));
		
	}

	@Override
	public Vehicle updateVehicle(Vehicle vehicle, Long id) {
		
		// we need to check whether vehicle with given id is exist in DB or not
		Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Vehicle", "Id", id)); 
		
		existingVehicle.setName(vehicle.getName());
		existingVehicle.setManufacturer(vehicle.getManufacturer());
		existingVehicle.setModel(vehicle.getModel());
		existingVehicle.setColor(vehicle.getColor());
		existingVehicle.setTopSpeed(vehicle.getTopSpeed());
		existingVehicle.setNbOfSeats(vehicle.getNbOfSeats());
		existingVehicle.setPrice(vehicle.getPrice());
		// save existing vehicle to DB
		vehicleRepository.save(existingVehicle);
		return existingVehicle;
	}

	@Override
	public void deleteVehicle(Long id) {
		
		// check whether a vehicle exist in a DB or not
		vehicleRepository.findById(id).orElseThrow(() -> 
								new ResourceNotFoundException("Vehicle", "Id", id));
		vehicleRepository.deleteById(id);
	}

	@Override
    public Vehicle returnVehicle(Long id) {
        // we need to check whether vehicle with given id is exist in DB or not
        Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "Id", id));
        existingVehicle.setAvailable(true);
        vehicleRepository.save(existingVehicle);

        return existingVehicle;

    }
	@Override
	public Vehicle rentVehicle(Long id) {
		// we need to check whether vehicle with given id is exist in DB or not
		Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Vehicle", "Id", id)); 
		existingVehicle.setAvailable(false);
		vehicleRepository.save(existingVehicle);
		return existingVehicle;
		
	}
	
}
