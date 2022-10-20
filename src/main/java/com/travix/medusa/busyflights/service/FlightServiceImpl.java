package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.api.CrazyAirMapper;
import com.travix.medusa.busyflights.api.ToughJetMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.base.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private CrazyAirMapper crazyAirMapper;

    @Autowired
    private ToughJetMapper toughJetMapper;

    @Override
    public BusyFlightsResponse getOneFlight(final BusyFlightsRequest busyFlightsRequest) {
        return null;
    }

    @Override
    public List<BusyFlightsResponse> getAllFlights(final BusyFlightsRequest busyFlightsRequest){
        final List<BusyFlightsResponse> allFlights = new ArrayList<>();
        final List<BusyFlightsResponse> flightsFromCrazyAir;
        final List<BusyFlightsResponse> flightsFromToughJet;

        flightsFromToughJet = this.toughJetMapper.getFlights(busyFlightsRequest);
        flightsFromCrazyAir = this.crazyAirMapper.getFlights(busyFlightsRequest);

        allFlights.addAll(flightsFromCrazyAir);
        allFlights.addAll(flightsFromToughJet);
        allFlights.stream()
                .sorted(Comparator.comparing(BusyFlightsResponse::getFare))
                .collect(Collectors.toList());

        return allFlights;
    }
}