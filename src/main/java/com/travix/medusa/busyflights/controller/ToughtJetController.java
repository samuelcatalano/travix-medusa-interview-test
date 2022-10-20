package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.exception.ErrorMessage;
import com.travix.medusa.busyflights.exception.FlightNotFoundException;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.apache.commons.collections4.CollectionUtils;
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
public class ToughtJetController {

    private static final Logger LOG = LoggerFactory.getLogger(ToughtJetController.class);

    @Autowired
    private ToughJetService service;

    @PostMapping(value = "/toughJet", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> searchforFlights(@Valid @RequestBody final ToughJetRequest toughJetRequest) throws FlightNotFoundException {
        final List<ToughJetResponse> flights = this.service.getFlights(toughJetRequest);

        if(flights == null) {
            throw new FlightNotFoundException("Error creating flights");
        } else if(CollectionUtils.isEmpty(flights)) {
            final ErrorMessage error = new ErrorMessage()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .code(500)
                    .message("Could not find any flight with these criteria!");

            LOG.error(error.getMessage());
            //return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(error);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(flights);
    }

    @PostMapping(value = "/toughJetOneFlight", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> searchforOneFlight(@Valid @RequestBody final ToughJetRequest toughJetRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(this.service.getOneFlight(toughJetRequest));
        } catch (final FlightNotFoundException e) {
            final ErrorMessage error = new ErrorMessage()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .code(500)
                    .message(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}