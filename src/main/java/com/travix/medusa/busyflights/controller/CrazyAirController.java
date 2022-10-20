package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.exception.ErrorMessage;
import com.travix.medusa.busyflights.exception.FlightNotFoundException;
import com.travix.medusa.busyflights.service.CrazyAirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CrazyAirController {

    private static final Logger LOG = LoggerFactory.getLogger(CrazyAirController.class);

    @Autowired
    private CrazyAirService service;

    @PostMapping(value = "/crazyAir", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> searchforFlights(@Valid @RequestBody final CrazyAirRequest crazyAirRequest) throws FlightNotFoundException {
        final List<CrazyAirResponse> flights = this.service.getFlights(crazyAirRequest);

        if (flights == null) {
            throw new FlightNotFoundException("Error creating flights");
        } else if (flights.isEmpty()) {
            final ErrorMessage error = new ErrorMessage()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .code(500)
                    .message("Could not find any flight with these criteria!");

            LOG.error(error.getMessage());
            //return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(flights);
    }

    @PostMapping(value = "/crazyAirOneFlight", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> searchforOneFlight(@Valid @RequestBody final CrazyAirRequest crazyAirRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.service.getOneFlight(crazyAirRequest));
        } catch (final FlightNotFoundException e) {
            final ErrorMessage error = new ErrorMessage()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .code(500)
                    .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}