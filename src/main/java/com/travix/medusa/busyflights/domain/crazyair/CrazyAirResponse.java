package com.travix.medusa.busyflights.domain.crazyair;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.travix.medusa.busyflights.domain.BaseEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@JsonDeserialize(builder = CrazyAirResponse.CrazyAirResponseBuilder.class)
@Value
@Builder
public class CrazyAirResponse extends BaseEntity {

    private String airline;
    private double price;
    private String cabinclass;
    private String departureAirportCode;
    private String destinationAirportCode;
    private String departureDate;
    private String arrivalDate;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CrazyAirResponseBuilder {}

}
