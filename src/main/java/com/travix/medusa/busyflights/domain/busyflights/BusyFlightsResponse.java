package com.travix.medusa.busyflights.domain.busyflights;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
@JsonDeserialize(builder = BusyFlightsResponse.BusyFlightsResponseBuilder.class)
@Value
@Builder
public class BusyFlightsResponse implements Serializable {

    private String airline;
    private String supplier;
    private BigDecimal fare;
    private String departureAirportCode;
    private String destinationAirportCode;
    private String departureDate;
    private String arrivalDate;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BusyFlightsResponseBuilder {
    }
}