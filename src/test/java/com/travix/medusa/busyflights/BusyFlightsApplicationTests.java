package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.controller.BusyFlightsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BusyFlightsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BusyFlightsController flightsController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testContexLoads() {
        assertThat(this.flightsController).isNotNull();
    }

    @Test
    public void testSearchToughJetFlights() throws Exception {
        final String json = "{\"from\":\"GRU\",\"to\":\"LHR\"," +
				"\"outboundDate\":\"2020-02-19:12:00:00\"," +
				"\"inboundDate\":\"2020-02-20:12:30:00\"," +
				"\"numberOfAdults\":\"2\"}";

        this.mockMvc.perform(post("/toughJet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchToughJetFlightsWrongPath() throws Exception {
        final String json = "{\"from\":\"GRU\",\"to\":\"LHR\"," +
				"\"outboundDate\":\"2020-02-19:12:00:00\"," +
				"\"inboundDate\":\"2020-02-20:12:30:00\"," +
				"\"numberOfAdults\":\"2\"}";

        this.mockMvc.perform(post("/XPTO")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchCrazyAirFlights() throws Exception {
        final String json = "{\"origin\":\"LHR\",\"destination\":\"GRU\"," +
				"\"departureDate\":\"2020-02-19:12:00:00\"," +
				"\"returnDate\":\"2020-02-20:12:30:00\"," +
				"\"passengerCount\":\"2\"}";

        this.mockMvc.perform(post("/crazyAir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchCrazyAirFlightsWrongPath() throws Exception {
        final String json = "{\"origin\":\"LHR\",\"destination\":\"GRU\"," +
				"\"departureDate\":\"2020-02-19:12:00:00\"," +
				"\"returnDate\":\"2020-02-20:12:30:00\"," +
				"\"passengerCount\":\"2\"}";

        this.mockMvc.perform(post("/ZYZQ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchFlightsMissingParameter() throws Exception {
        final String missingParametherRequest = "{\n" +
                "  \"destination\" : \"LHR\",\n" +
                "  \"departureDate\": \"\",\n" +
                "  \"returnDate\": \"\",\n" +
                "  \"numberOfPassengers\": 1\n" +
                "}";

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/search")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(missingParametherRequest);

        final MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        Assert.assertThat(result.getResponse().getContentAsString(), containsString(""));
    }
}