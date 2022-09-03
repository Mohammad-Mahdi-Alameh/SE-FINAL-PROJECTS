package com.example.rently.payloads.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RentResponse {
    
    @NotNull
    private Long userId;

    @NotNull
    private Long vehicleId;

    @NotEmpty
    private LocalDateTime createDateTime;;



    public RentResponse(Long userId , Long vehicleId , LocalDateTime createDateTime){
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.createDateTime = createDateTime;

    }

    
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

	public LocalDateTime getcreateDateTime() {
		return this.createDateTime;
	}

	public void setcreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
    
}
