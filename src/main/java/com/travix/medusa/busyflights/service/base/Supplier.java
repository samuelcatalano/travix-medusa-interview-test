package com.travix.medusa.busyflights.service.base;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

public interface Supplier {

    List<BusyFlightsResponse> getFlights(final BusyFlightsRequest busyFlightsRequest) throws Exception;
}