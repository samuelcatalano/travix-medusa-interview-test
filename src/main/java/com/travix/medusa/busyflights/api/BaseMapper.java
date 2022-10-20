package com.travix.medusa.busyflights.api;

import com.travix.medusa.busyflights.domain.BaseEntity;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

public abstract class BaseMapper<R extends BaseEntity, Q extends BaseEntity> {

    protected abstract R convertBusyFlightsRequestToSuppliersRequests(final BusyFlightsRequest busyFlightsRequest);
    protected abstract List<BusyFlightsResponse> convertResponsesToBusyFlightsRequest(final List<Q> responseList);
}