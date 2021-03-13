package com.starling.domain.services;

import com.starling.infrastructure.starlingapi.AccountsResponse;
import com.starling.infrastructure.starlingapi.StarlingApiClient;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavingsGoalServiceTest {

    private SavingsGoalService savingsGoalService;

    @Mock
    private StarlingApiClient mockStarlingApiClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        savingsGoalService = new SavingsGoalService(mockStarlingApiClient);
    }

    @Test
    public void shouldAddRoundUpToSavingsGoalForGivenWeek() {
        AccountsResponse.Account acc0 = new AccountsResponse.Account("id0", "PRIMARY", "cat0", "GBP");
        AccountsResponse accountsResponseMock = new AccountsResponse(Arrays.asList(acc0));

        TransactionFeedItemResponse transactionFeedItemResponseMock = mock(TransactionFeedItemResponse.class);

        when(mockStarlingApiClient.getAccounts()).thenReturn(accountsResponseMock);
        when(mockStarlingApiClient.getFeedItemsBetweenTimestamps(anyString(), anyString(), anyMap())).thenReturn(transactionFeedItemResponseMock);

        savingsGoalService.addRoundUpToSavingsGoal("sav0", 2021, 3);
    }

}
