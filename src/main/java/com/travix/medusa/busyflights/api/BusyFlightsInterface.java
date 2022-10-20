package com.travix.medusa.busyflights.api;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

public interface BusyFlightsInterface {

    List<BusyFlightsResponse> getFlights(final BusyFlightsRequest busyFlightsRequest);

    BusyFlightsResponse getOneFlight(final BusyFlightsRequest busyFlightsRequest);
}