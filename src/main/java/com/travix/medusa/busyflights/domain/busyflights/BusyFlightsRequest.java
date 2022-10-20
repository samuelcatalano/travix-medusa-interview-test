package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.busyflights.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BusyFlightsRequest extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 3)
    private String origin;

    @NotNull
    @Size(min = 3, max = 3)
    private String destination;

    @NotNull(message = "Departure Date cannot be null")
    private String departureDate;

    @NotNull(message = "Return Date cannot be null")
    private String returnDate;

    @Min(1)
    @Max(4)
    private int numberOfPassengers;
}
