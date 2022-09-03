package com.example.rently.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rently.models.Rent;
import com.example.rently.models.User;
import com.example.rently.models.Vehicle;
@Repository

public interface RentRepository  extends JpaRepository<Rent, Long> {
    //method to get all rents of a specific user
    List<Rent> findAllByUser(User user);

    //method to count all rents of a specific vehicle
    Integer countByVehicle(Vehicle vehicle);

}
