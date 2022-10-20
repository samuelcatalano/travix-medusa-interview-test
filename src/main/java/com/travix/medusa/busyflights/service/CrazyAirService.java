package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.exception.FlightNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CrazyAirService {

    private static final Logger LOG = LoggerFactory.getLogger(CrazyAirService.class);

    private final List<CrazyAirResponse> defaultFlights;
    public static final String NAME = "CrazyAir";

    /**
     * Constructor.
     */
    public CrazyAirService() {
        this.defaultFlights = this.getDefaultFlights();
    }

    public CrazyAirResponse getOneFlight(final CrazyAirRequest crazyAirRequest) throws FlightNotFoundException {
        if (CollectionUtils.isEmpty(this.getFlights(crazyAirRequest))) {
            throw new FlightNotFoundException("There is no flight with these criteria!");
        } else {
            return this.getFlights(crazyAirRequest).get(0);
        }
    }

    public List<CrazyAirResponse> getFlights(final CrazyAirRequest crazyAirRequest) {
        return this.buildMatchedFlights(crazyAirRequest);
    }

    private List<CrazyAirResponse> buildMatchedFlights(final CrazyAirRequest crazyAirRequest) {
        Boolean hasFlight = null;

        for (final CrazyAirResponse response : this.defaultFlights) {
            hasFlight = this.checkMatchedFlight(crazyAirRequest, response);
        }
        if (hasFlight) {
            return this.defaultFlights;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean checkMatchedFlight(final CrazyAirRequest crazyAirRequest, final CrazyAirResponse crazyAirResponse) {
        if (!StringUtils.isEmpty(crazyAirRequest.getOrigin()) &&
                !crazyAirRequest.getOrigin().equalsIgnoreCase(crazyAirResponse.getDepartureAirportCode())) {
            return false;
        }
        if (!StringUtils.isEmpty(crazyAirRequest.getDestination()) &&
                !crazyAirRequest.getDestination().equalsIgnoreCase(crazyAirResponse.getDestinationAirportCode())) {
            return false;
        }
        if (!StringUtils.isEmpty(crazyAirRequest.getDepartureDate())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date date1 = dateFormat.parse(crazyAirRequest.getDepartureDate());
                final Date date2 = dateFormat.parse(crazyAirResponse.getDepartureDate());

                if (!date1.equals(date2)) {
                    return false;
                }
            } catch (final ParseException e) {
                LOG.error(e.getMessage());
            }
        }

        if (!StringUtils.isEmpty(crazyAirRequest.getReturnDate())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date date1 = dateFormat.parse(crazyAirRequest.getReturnDate());
                final Date date2 = dateFormat.parse(crazyAirResponse.getArrivalDate());

                if (!date1.equals(date2)) {
                    return false;
                }
            } catch (final ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        return true;
    }

    private List<CrazyAirResponse> getDefaultFlights() {
        final List<CrazyAirResponse> crazyAirResponse = new ArrayList<>();

        final LocalDateTime dep1 = LocalDateTime.of(2020, Month.FEBRUARY, 19, 12, 30);
        final LocalDateTime arv1 = LocalDateTime.of(2020, Month.FEBRUARY, 20, 12, 30);

        final CrazyAirResponse flight1 = CrazyAirResponse.builder()
                .airline("LATAM")
                .price(560)
                .cabinclass("Executive")
                .departureAirportCode("LHR")
                .destinationAirportCode("GRU")
                .departureDate(dep1.format(DateTimeFormatter.ISO_DATE_TIME))
                .arrivalDate(arv1.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        final LocalDateTime dep2 = LocalDateTime.of(2019, Month.MARCH, 3, 6, 45);
        final LocalDateTime arv2 = LocalDateTime.of(2019, Month.MARCH, 4, 6, 45);

        final CrazyAirResponse flight2 = CrazyAirResponse.builder()
                .airline("British Airways")
                .price(620)
                .cabinclass("Executive")
                .departureAirportCode("LHR")
                .destinationAirportCode("GRU")
                .departureDate(dep2.format(DateTimeFormatter.ISO_DATE_TIME))
                .arrivalDate(arv2.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return Arrays.asList(flight1, flight2);
    }
}