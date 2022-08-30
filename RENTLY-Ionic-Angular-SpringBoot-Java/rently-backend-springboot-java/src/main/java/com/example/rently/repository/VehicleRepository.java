package com.example.rently.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rently.models.Vehicle;
@Repository

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
