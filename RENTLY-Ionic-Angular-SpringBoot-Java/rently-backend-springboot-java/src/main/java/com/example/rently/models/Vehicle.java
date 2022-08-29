package com.example.rently.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
	@Size(max = 50)
    private String name;

    @NotBlank
	@Size(max = 50)
    private String manufacturer;

    @NotBlank
	@Size(max = 50)
    private String model;

    @NotBlank
	@Size(max = 15)
    private String color;

    @NotBlank
	@Size(max = 3)
    private int topSpeed;

    @NotBlank
	@Size(max = 3)
    private int nbOfSeats;

    @NotBlank
	@Size(max = 1)
    private int status;

    @NotBlank
	@Size(max = 1)
    private int type;

    @NotBlank
	@Size(max = 5)
    private int price;





    
}
