package com.example.rently.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rently.exceptions.ResourceNotFoundException;
import com.example.rently.models.Rent;
import com.example.rently.models.Vehicle;
import com.example.rently.models.User;
import com.example.rently.repository.RentRepository;
import com.example.rently.repository.UserRepository;
import com.example.rently.repository.VehicleRepository;
import com.example.rently.services.RentService;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    RentRepository rentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    @Override
	public Rent getRentById(Long id) {
		return rentRepository.findById(id).orElseThrow(() -> 
						new ResourceNotFoundException("Rent", "Id", id));
		
	}

    @Override
    public Rent rentVehicle(Rent rent) {
        //create a rent
        // we need to check whether vehicle with given id is exist in DB or not
        Long vehicleId = rent.getVehicle().getId();
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "Id", vehicleId));
        existingVehicle.setAvailable(false);
        vehicleRepository.save(existingVehicle);

        return rentRepository.save(rent);

    }

    @Override
    @Transactional
    public List<Rent> loadRentsOfUser(Long userId) {
            // Long userId = user.getId();
            User existingUser = userRepository.findById(userId).orElseThrow(() -> 
            new ResourceNotFoundException("User", "Id", userId));
            List<Rent> rents = rentRepository.findAllByUser(existingUser);
            return rents;
        }

    @Override
    public Integer countVehicleRents(Long vehicleId){
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "Id", vehicleId));
        Integer totalVehicleRents = rentRepository.countByVehicle(existingVehicle);
        return totalVehicleRents;
    }
    @Override
    public Integer countUserRents(Long userId){
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", userId));
        Integer totalUserRents = rentRepository.countByUser(existingUser);
        return totalUserRents;
    }

    

}
