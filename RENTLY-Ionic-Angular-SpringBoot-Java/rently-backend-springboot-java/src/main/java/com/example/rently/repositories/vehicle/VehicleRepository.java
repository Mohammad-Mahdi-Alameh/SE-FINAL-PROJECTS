package com.example.rently.repositories.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rently.models.vehicle.Vehicle;
@Repository

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
