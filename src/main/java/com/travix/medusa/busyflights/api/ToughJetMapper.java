package com.travix.medusa.busyflights.api;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.ToughJetService;
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
public class ToughJetMapper extends BaseMapper<ToughJetRequest, ToughJetResponse> implements BusyFlightsInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.toughJet.service}")
    private String url;

    @Override
    public BusyFlightsResponse getOneFlight(final BusyFlightsRequest busyFlightsRequest) {
        final ToughJetRequest toughJetRequest = this.convertBusyFlightsRequestToSuppliersRequests(busyFlightsRequest);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<ToughJetRequest> entity = new HttpEntity<>(toughJetRequest, headers);

        final ResponseEntity<List<ToughJetResponse>> toughJetResponse = this.restTemplate.exchange(
                this.url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<ToughJetResponse>>() {});

        return this.convertResponseToBusyFlightResponse(toughJetResponse.getBody().get(0));
    }

    @Override
    public List<BusyFlightsResponse> getFlights(final BusyFlightsRequest busyFlightsRequest) {
        final ToughJetRequest toughJetRequest = this.convertBusyFlightsRequestToSuppliersRequests(busyFlightsRequest);

        final MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<ToughJetRequest> entity = new HttpEntity<>(toughJetRequest, headers);

        final ResponseEntity<List<ToughJetResponse>> toughJetResponse = this.restTemplate.exchange(
                this.url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<ToughJetResponse>>() {});

        return this.convertResponsesToBusyFlightsRequest(toughJetResponse.getBody());
    }

    @Override
    protected ToughJetRequest convertBusyFlightsRequestToSuppliersRequests(final BusyFlightsRequest busyFlightsRequest) {
        final ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
        toughJetRequest.setTo(busyFlightsRequest.getDestination());
        toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
        toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
        toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());

        return toughJetRequest;
    }

    @Override
    protected List<BusyFlightsResponse> convertResponsesToBusyFlightsRequest(final List<ToughJetResponse> toughJetResponseList) {
        final List<BusyFlightsResponse> flights = new ArrayList<>();

        toughJetResponseList.stream().parallel().forEachOrdered(response -> {
            final BusyFlightsResponse busyFlightsResponse = BusyFlightsResponse
                    .builder()
                    .airline(response.getCarrier())
                    .supplier(ToughJetService.NAME)
                    .fare(new BigDecimal(response.getBasePrice()).setScale(2, RoundingMode.CEILING))
                    .departureAirportCode(response.getDepartureAirportName())
                    .destinationAirportCode(response.getArrivalAirportName())
                    .departureDate(response.getOutboundDateTime())
                    .arrivalDate(response.getInboundDateTime())
                    .build();

            flights.add(busyFlightsResponse);
        });

        return flights;
    }

    public BusyFlightsResponse convertResponseToBusyFlightResponse(final ToughJetResponse toughJetResponse) {
        return BusyFlightsResponse.builder()
                .airline(toughJetResponse.getCarrier())
                .supplier(ToughJetService.NAME)
                .fare(BigDecimal.valueOf(toughJetResponse.getBasePrice()).setScale(2, RoundingMode.CEILING))
                .departureAirportCode(toughJetResponse.getDepartureAirportName())
                .destinationAirportCode(toughJetResponse.getArrivalAirportName())
                .departureDate(toughJetResponse.getOutboundDateTime())
                .arrivalDate(toughJetResponse.getInboundDateTime())
                .build();
    }
}