package com.example.rently.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rently.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
