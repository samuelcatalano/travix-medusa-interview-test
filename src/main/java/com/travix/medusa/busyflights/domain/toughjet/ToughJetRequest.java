package com.travix.medusa.busyflights.domain.toughjet;

import com.travix.medusa.busyflights.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
public class ToughJetRequest extends BaseEntity {

    @NotNull
    @Size(min = 3, max = 3)
    private String from;

    @NotNull
    @Size(min = 3, max = 3)
    private String to;

    @NotNull(message = "Outbound Date cannot be null")
    private String outboundDate;

    @NotNull(message = "Inbound Date cannot be null")
    private String inboundDate;

    @Min(value = 1, message = "Min Number of passengers = 1")
    @Max(value = 4, message = "Max Number of passengers = 4")
    private int numberOfAdults;
}
