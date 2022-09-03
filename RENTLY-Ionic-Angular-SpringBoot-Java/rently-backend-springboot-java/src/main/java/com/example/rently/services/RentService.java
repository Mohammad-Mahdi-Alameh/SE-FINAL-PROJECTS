package com.example.rently.services;

import java.util.List;

import com.example.rently.models.Rent;

public interface RentService {
    // Rent addRent(Rent rent);
	List<Rent> getAllRents();
	List<Rent> loadRentsOfUser(Long userId) ;
    Integer countVehicleRents(Long rentId);
	Rent getRentById(Long id);
    Rent rentVehicle(Rent rent);
    // ResponseEntity<Long> finishRent(Long id);
    
}
