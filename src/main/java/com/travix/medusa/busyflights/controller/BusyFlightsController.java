package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.exception.ErrorMessage;
import com.travix.medusa.busyflights.service.base.FlightService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BusyFlightsController {

    @Autowired
    private FlightService service;

    @PostMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> searchforFlights(@Valid @RequestBody final BusyFlightsRequest busyFlightsRequest) throws Exception {
        final List<BusyFlightsResponse> flights = this.service.getAllFlights(busyFlightsRequest);

        if (CollectionUtils.isNotEmpty(flights)) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(flights);
        } else {
            final ErrorMessage error = new ErrorMessage()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .code(500)
                    .message("Could not find any flight with these criteria!");

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
    }
}