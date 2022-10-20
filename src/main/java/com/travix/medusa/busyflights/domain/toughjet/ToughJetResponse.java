package com.travix.medusa.busyflights.domain.toughjet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.travix.medusa.busyflights.domain.BaseEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode
@JsonDeserialize(builder = ToughJetResponse.ToughJetResponseBuilder.class)
@Value
@Builder
public class ToughJetResponse extends BaseEntity {

    private String carrier;
    private double basePrice;
    private double tax;
    private double discount;
    private String departureAirportName;
    private String arrivalAirportName;
    private String outboundDateTime;
    private String inboundDateTime;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ToughJetResponseBuilder {}
}