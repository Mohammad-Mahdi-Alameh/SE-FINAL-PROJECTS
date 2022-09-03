package com.example.rently.payloads.request;

import javax.validation.constraints.NotNull;

public class RentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long vehicleId;

    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
    
}
