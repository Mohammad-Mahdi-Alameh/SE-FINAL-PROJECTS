package com.example.rently.services.rentService.rentServcieImplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rently.exceptions.ResourceNotFoundException;
import com.example.rently.models.rent.Rent;
import com.example.rently.models.vehicle.Vehicle;
import com.example.rently.models.user.User;
import com.example.rently.repositories.rent.RentRepository;
import com.example.rently.repositories.user.UserRepository;
import com.example.rently.repositories.vehicle.VehicleRepository;
import com.example.rently.services.rentService.RentService;

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
