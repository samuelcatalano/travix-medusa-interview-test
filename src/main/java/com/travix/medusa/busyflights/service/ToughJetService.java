package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
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
public class ToughJetService {

    private static final Logger LOG = LoggerFactory.getLogger(ToughJetService.class);

    private final List<ToughJetResponse> defaultFlights;
    public static final String NAME = "ToughJet";

    /**
     * Constructor.
     */
    public ToughJetService() {
        this.defaultFlights = this.getDefaultFlights();
    }

    public ToughJetResponse getOneFlight(final ToughJetRequest toughJetRequest) throws FlightNotFoundException {
        if (CollectionUtils.isEmpty(this.getFlights(toughJetRequest))) {
            throw new FlightNotFoundException("There is no flight with these criteria!");
        } else {
            return this.getFlights(toughJetRequest).get(0);
        }
    }

    public List<ToughJetResponse> getFlights(final ToughJetRequest toughJetRequest) {
        return this.buildMatchedFlights(toughJetRequest);
    }

    private List<ToughJetResponse> buildMatchedFlights(final ToughJetRequest toughJetRequest) {
        Boolean hasFlight = null;

        for (final ToughJetResponse response : this.defaultFlights) {
            hasFlight = this.checkMatchedFlight(toughJetRequest, response);
        }
        if (hasFlight) {
            return this.defaultFlights;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean checkMatchedFlight(final ToughJetRequest toughJetRequest, final ToughJetResponse toughJetResponse) {
        if (!StringUtils.isEmpty(toughJetRequest.getFrom()) &&
                !toughJetRequest.getFrom().equalsIgnoreCase(toughJetResponse.getDepartureAirportName())) {
            return false;
        }
        if (!StringUtils.isEmpty(toughJetRequest.getTo()) &&
                !toughJetRequest.getTo().equalsIgnoreCase(toughJetResponse.getArrivalAirportName())) {
            return false;
        }
        if (!StringUtils.isEmpty(toughJetRequest.getOutboundDate())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date date1 = dateFormat.parse(toughJetRequest.getOutboundDate());
                final Date date2 = dateFormat.parse(toughJetResponse.getOutboundDateTime());

                if (!date1.equals(date2)) {
                    return false;
                }
            } catch (final ParseException e) {
                LOG.error(e.getMessage());
            }
        }

        if (!StringUtils.isEmpty(toughJetRequest.getInboundDate())) {
            try {
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date date1 = dateFormat.parse(toughJetRequest.getInboundDate());
                final Date date2 = dateFormat.parse(toughJetResponse.getInboundDateTime());

                if (!date1.equals(date2)) {
                    return false;
                }
            } catch (final ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        return true;
    }

    private List<ToughJetResponse> getDefaultFlights() {
        final LocalDateTime dep1 = LocalDateTime.of(2020, Month.FEBRUARY, 19, 12, 30);
        final LocalDateTime arv1 = LocalDateTime.of(2020, Month.FEBRUARY, 20, 12, 30);

        final ToughJetResponse flight1 = ToughJetResponse.builder()
                .carrier("LATAM")
                .basePrice(375)
                .tax(15)
                .discount(0)
                .departureAirportName("GRU")
                .arrivalAirportName("LHR")
                .outboundDateTime(dep1.format(DateTimeFormatter.ISO_DATE_TIME))
                .inboundDateTime(arv1.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        final LocalDateTime dep2 = LocalDateTime.of(2019, Month.MARCH, 3, 6, 45);
        final LocalDateTime arv2 = LocalDateTime.of(2019, Month.MARCH, 4, 6, 45);

        final ToughJetResponse flight2 = ToughJetResponse.builder()
                .carrier("British Airways")
                .basePrice(450)
                .tax(75)
                .discount(0)
                .departureAirportName("GRU")
                .arrivalAirportName("LHR")
                .outboundDateTime(dep2.format(DateTimeFormatter.ISO_DATE_TIME))
                .inboundDateTime(arv2.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return Arrays.asList(flight1, flight2);
    }
}