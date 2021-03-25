package com.starling.application;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RoundUpControllerIntegrationTest {

    private static WireMockServer starlingApiMockServer;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    static void setupMockServer() {
        starlingApiMockServer = new WireMockServer(options().port(8090));
        starlingApiMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        starlingApiMockServer.stop();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void addWeekRoundUpsToSavingsGoalShouldReturn204() throws Exception {
        this.mockMvc
                .perform(put("/savings-goal/sg1/add-round-ups/2021/10")
                        .header("Authorization", "Bearer abc123"))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

}
