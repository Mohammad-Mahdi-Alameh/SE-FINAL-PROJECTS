package com.example.rently.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rently.models.Vehicle;
import com.example.rently.services.VehicleService;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
	
	private VehicleService vehicleService;

	public VehicleController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	// build create vehicle REST API
	@PostMapping("/add")
	public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle){
		return new ResponseEntity<Vehicle>(vehicleService.addVehicle(vehicle), HttpStatus.CREATED);
	}
	
}
