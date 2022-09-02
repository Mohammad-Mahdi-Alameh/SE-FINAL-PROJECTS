package com.example.rently.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rently.models.Rent;
import com.example.rently.models.Vehicle;
import com.example.rently.payload.request.RentRequest;
import com.example.rently.payload.response.MessageResponse;
import com.example.rently.payload.response.RentResponse;
import com.example.rently.security.services.UserService;
import com.example.rently.services.RentService;
import com.example.rently.services.VehicleService;

@RestController
@RequestMapping("/api/v1/rents")
public class RentController {
    @Autowired
    RentService rentService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    UserService userService;

    // build create rent RESTful API
    @PostMapping("/rent")
    public ResponseEntity<?> rentVehicle(@Valid @RequestBody RentRequest rentRequest) {
        Long requestedVehicleId = rentRequest.getVehicleId();
        Vehicle requestedVehicle = vehicleService.getVehicleById(requestedVehicleId);
        if (requestedVehicle.isAvailable()) {
            requestedVehicle = vehicleService.rentVehicle(requestedVehicleId);
                 Rent rent = rentService.rentVehicle(new Rent(vehicleService.getVehicleById(requestedVehicleId),
                            userService.getUserById(rentRequest.getUserId())));
            return new ResponseEntity<RentResponse>(
                    new RentResponse(rent.getVehicle().getId(),
                            rent.getUser().getId(),rent.getCreateDateTime()),
                    HttpStatus.CREATED);
        }else{
        
        return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Vehicle isn't available right now!"));
        }
    }

    // build get all rents RESTful API
    @GetMapping
    public List<Rent> getAllRents() {
        return rentService.getAllRents();
    }


}
