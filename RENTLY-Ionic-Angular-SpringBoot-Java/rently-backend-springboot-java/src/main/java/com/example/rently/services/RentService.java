package com.example.rently.services;

import java.util.List;

import com.example.rently.models.Rent;

public interface RentService {
    // Rent addRent(Rent rent);
    List<Rent> getAllRents();

    // function to count all rents of a specific user
    Integer countUserRents(Long UserId);
   
    // function to load all rents of a specifc user
    List<Rent> loadRentsOfUser(Long userId);

    // function to count all rents of a specific vehicle
    Integer countVehicleRents(Long rentId);

    Rent getRentById(Long id);

    Rent rentVehicle(Rent rent);

}
