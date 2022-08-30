package com.example.rently.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
	@Size(max = 50)
    @Column(name = "name" )
    private String name;

    @NotEmpty
	@Size(max = 50)
    @Column(name = "manufacturer" )
    private String manufacturer;

    @NotEmpty
	@Size(max = 50)
    @Column(name = "model" )
    private String model;

    @NotEmpty
	@Size(max = 15)
    @Column(name = "color" )
    private String color;

    @NotNull
    @Column(name = "top_speed" )
    private int topSpeed;

    @NotEmpty
	@Size(max = 10)
    @Column(name = "transmission" )
    private String transmission;

    @NotNull
    @Column(name = "nb_of_seats" )
    private int nbOfSeats;

    @NotNull
    @Column(name = "status" )
    private int status;

    @NotNull
    @Column(name = "type" )
    private int type;

    @NotNull
    @Column(name = "price" )
    private int price;

    @NotNull
    @Column(name = "availability" )
    private boolean availability;

    @NotNull
    @Column(name = "count" )
    private int count;





    
}
