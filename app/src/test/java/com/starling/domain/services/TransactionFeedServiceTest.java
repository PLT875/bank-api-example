package com.starling.domain.services;

import com.starling.infrastructure.starlingapi.StarlingApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransactionFeedServiceTest {

    private TransactionFeedService transactionFeedService;

    @Mock
    private StarlingApiClient mockStarlingApiClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transactionFeedService = new TransactionFeedService(mockStarlingApiClient);
    }

    @Test
    public void shouldRetrieveTransactionFeedBetweenTimestamps() {
        String minTransactionTs = "2021-03-08T00:00Z";
        String maxTransactionTs = "2021-03-14T23:59:59.999999999Z";
        transactionFeedService.retrieveTransactionFeedBetweenTimestamps("accountUid", "categoryUid", minTransactionTs, maxTransactionTs);
        verify(mockStarlingApiClient).getFeedItemsTransactionsBetween(anyString(), anyString(), anyMap());
    }
}
