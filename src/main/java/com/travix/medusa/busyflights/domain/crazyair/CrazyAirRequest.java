package com.travix.medusa.busyflights.domain.crazyair;

import com.travix.medusa.busyflights.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
public class CrazyAirRequest extends BaseEntity {

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

    @Min(value = 1, message = "Min Number of passengers = 1")
    @Max(value = 4, message = "Max Number of passengers = 4")
    private int passengerCount;
}