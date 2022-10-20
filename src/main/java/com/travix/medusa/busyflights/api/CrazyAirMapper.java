package com.travix.medusa.busyflights.api;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.CrazyAirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrazyAirMapper extends BaseMapper<CrazyAirRequest, CrazyAirResponse> implements BusyFlightsInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.crazyair.service}")
    private String url;

    @Override
    public BusyFlightsResponse getOneFlight(final BusyFlightsRequest busyFlightsRequest) {
        final CrazyAirRequest crazyAirRequest = this.convertBusyFlightsRequestToSuppliersRequests(busyFlightsRequest);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<CrazyAirRequest> entity = new HttpEntity<>(crazyAirRequest, headers);

        final ResponseEntity<List<CrazyAirResponse>> crazyAirResponse = this.restTemplate.exchange(
                this.url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<CrazyAirResponse>>() {});

        final CrazyAirResponse response = crazyAirResponse.getBody().get(0);
        return this.convertResponseToBusyFlightResponse(response);
    }

    @Override
    public List<BusyFlightsResponse> getFlights(final BusyFlightsRequest busyFlightsRequest) {
        final CrazyAirRequest crazyAirRequest = this.convertBusyFlightsRequestToSuppliersRequests(busyFlightsRequest);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<CrazyAirRequest> entity = new HttpEntity<>(crazyAirRequest, headers);

        final ResponseEntity<List<CrazyAirResponse>> crazyAirResponse = this.restTemplate.exchange(
                this.url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<CrazyAirResponse>>() {});

        return this.convertResponsesToBusyFlightsRequest(crazyAirResponse.getBody());
    }

    @Override
    protected CrazyAirRequest convertBusyFlightsRequestToSuppliersRequests(final BusyFlightsRequest busyFlightsRequest) {
        final CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(busyFlightsRequest.getOrigin());
        crazyAirRequest.setDestination(busyFlightsRequest.getDestination());
        crazyAirRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(busyFlightsRequest.getReturnDate());
        crazyAirRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());

        return crazyAirRequest;
    }

    @Override
    protected List<BusyFlightsResponse> convertResponsesToBusyFlightsRequest(final List<CrazyAirResponse> crazyAirResponseList) {
        final List<BusyFlightsResponse> flights = new ArrayList<>();

        crazyAirResponseList.stream().parallel().forEachOrdered(response -> {
            final BusyFlightsResponse busyFlightsResponse = BusyFlightsResponse
                    .builder()
                    .airline(response.getAirline())
                    .supplier(CrazyAirService.NAME)
                    .fare(new BigDecimal(response.getPrice()).setScale(2, RoundingMode.CEILING))
                    .departureAirportCode(response.getDepartureAirportCode())
                    .destinationAirportCode(response.getDestinationAirportCode())
                    .departureDate(response.getDepartureDate())
                    .arrivalDate(response.getArrivalDate())
                    .build();

            flights.add(busyFlightsResponse);
        });

        return flights;
    }

    public BusyFlightsResponse convertResponseToBusyFlightResponse(final CrazyAirResponse crazyAirResponse) {
        return BusyFlightsResponse.builder()
                .airline(crazyAirResponse.getAirline())
                .supplier(CrazyAirService.NAME)
                .departureAirportCode(crazyAirResponse.getDepartureAirportCode())
                .destinationAirportCode(crazyAirResponse.getDestinationAirportCode())
                .departureDate(crazyAirResponse.getDepartureDate())
                .arrivalDate(crazyAirResponse.getArrivalDate())
                .fare(BigDecimal.valueOf(crazyAirResponse.getPrice()).setScale(2, RoundingMode.CEILING))
                .build();
    }
}