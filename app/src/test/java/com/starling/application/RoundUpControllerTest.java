package com.starling.application;

import com.starling.domain.services.SavingsGoalService;
import feign.FeignException.Forbidden;
import feign.FeignException.InternalServerError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoundUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RoundUpController roundUpController;

    @Mock
    private SavingsGoalService mockSavingsGoalService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        roundUpController = new RoundUpController(mockSavingsGoalService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(roundUpController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void addWeekRoundUpsToSavingsGoalShouldReturn204() throws Exception {
        mockMvc
                .perform(put("/savings-goal/sgUid/add-round-ups/2021/3"))
                .andExpect(status().isNoContent());

        Mockito.verify(mockSavingsGoalService).addRoundUpToSavingsGoal("sgUid", 2021, 3);
    }

    @Test
    void addWeekRoundUpsToSavingsGoalShouldReturn403WhenApiClientForbidden() throws Exception {
        Forbidden mockForbidden = mock(Forbidden.class);
        doThrow(mockForbidden).when(mockSavingsGoalService).addRoundUpToSavingsGoal("sgUid", 2021, 3);
        when(mockForbidden.getMessage()).thenReturn("Invalid access token");

        mockMvc
                .perform(put("/savings-goal/sgUid/add-round-ups/2021/3"))
                .andExpect(status().isForbidden());
    }

    @Test
    void addWeekRoundUpsToSavingsGoalShouldReturn500WhenApiClientInternalError() throws Exception {
        InternalServerError mockInternal = mock(InternalServerError.class);
        doThrow(mockInternal).when(mockSavingsGoalService).addRoundUpToSavingsGoal("sgUid", 2021, 3);
        when(mockInternal.getMessage()).thenReturn("Internal server error");

        mockMvc
                .perform(put("/savings-goal/sgUid/add-round-ups/2021/3"))
                .andExpect(status().isInternalServerError());
    }

}
