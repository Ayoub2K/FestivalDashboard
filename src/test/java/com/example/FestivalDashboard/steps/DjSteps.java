package com.example.FestivalDashboard.steps;

import com.example.FestivalDashboard.controller.DjController;
import com.example.FestivalDashboard.domain.DJ;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest // Make sure this is included for Spring to initialize
public class DjSteps {

    private MockMvc mockMvc;

    @Autowired
    private DjController djController;

    @Autowired
    private ObjectMapper objectMapper; // For converting DJ objects to JSON

    private DJ dj;
    private ResultActions response;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(djController).build();
        Assert.assertNotNull(objectMapper);
    }

    @Given("I have a new DJ with the name {string}, genre {string}, and age {int}")
    public void i_have_a_new_dj_with_name_genre_and_age(String name, String genre, Integer age) {
        dj = new DJ(name, genre, age);
    }

    @When("I send a request to create the DJ")
    public void i_send_a_request_to_create_the_dj() throws Exception {
        // Use MockMvc to perform a POST request
        response = mockMvc.perform(MockMvcRequestBuilders.post("/api/dj")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dj))); // Convert DJ object to JSON
    }

    @Then("the response should return the created DJ with the name {string}")
    public void the_response_should_return_the_created_dj_with_name(String expectedName) throws Exception {
        // Verify that the response has status 200 (ok)
        Assert.assertEquals(200, response.andReturn().getResponse().getStatus()); // Change 200 to 201

        String jsonResponse = response.andReturn().getResponse().getContentAsString();
        DJ createdDj = objectMapper.readValue(jsonResponse, DJ.class); // Deserialize JSON back to DJ object
        Assert.assertNotNull(createdDj);
        Assert.assertEquals(expectedName, createdDj.getName());
    }

    @Given("a DJ with the ID {int} exists")
    public void a_dj_with_id_exists(int id) {
        // Mock or assume a DJ with the specified ID exists.
        dj = new DJ("DJ Mike", "Techno", 35);
        dj.setId((long) id);
    }

    @When("I send a request to get the DJ by ID")
    public void i_send_a_request_to_get_the_dj_by_id() throws Exception {
        // Use MockMvc to perform a GET request
        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/dj/" + dj.getId())
                .accept(MediaType.APPLICATION_JSON)); // Set expected response type
    }

    @Then("the response should return the DJ with the name {string}")
    public void the_response_should_return_the_dj_with_the_name(String expectedName) throws Exception {
        String jsonResponse = response.andReturn().getResponse().getContentAsString();
        DJ returnedDj = objectMapper.readValue(jsonResponse, DJ.class); // Deserialize JSON back to DJ object
        Assert.assertNotNull(returnedDj);
        Assert.assertEquals(expectedName, returnedDj.getName());
    }
}