package com.starling.domain.services;

import com.starling.infrastructure.starlingapi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.starling.domain.util.SavingsGoalUtil.FEED_ITEM_OUT;
import static com.starling.domain.util.SavingsGoalUtil.PAYMENTS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavingsGoalServiceTest {

    private SavingsGoalService savingsGoalService;

    @Mock
    private StarlingApiClient mockStarlingApiClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        savingsGoalService = new SavingsGoalService(mockStarlingApiClient);
    }

    @Test
    void shouldAddRoundUpsToSavingsGoalForGivenWeek() {
        AccountsResponse.Account acc0 = new AccountsResponse.Account("id0", "PRIMARY", "cat0", "GBP");
        AccountsResponse accountsResponseMock = new AccountsResponse(Arrays.asList(acc0));

        FeedItem feedItem0 = new FeedItem(FEED_ITEM_OUT, PAYMENTS, new FeedItem.Amount("GB", Integer.valueOf(435)));
        FeedItem feedItem1 = new FeedItem(FEED_ITEM_OUT, PAYMENTS, new FeedItem.Amount("GB", Integer.valueOf(520)));
        List<FeedItem> feedItems = Arrays.asList(feedItem0, feedItem1);
        TransactionFeedItemResponse transactionFeedItemResponseMock = new TransactionFeedItemResponse(feedItems);

        when(mockStarlingApiClient.getAccounts()).thenReturn(accountsResponseMock);
        when(mockStarlingApiClient.getFeedItemsBetweenTimestamps(anyString(), anyString(), anyMap()))
                .thenReturn(transactionFeedItemResponseMock);

        SavingsGoalTopUpResponse responseMock = new SavingsGoalTopUpResponse("transferUid", true, Collections.emptyList());

        when(mockStarlingApiClient.addMoneyToSavingsGoal(anyString(), anyString(), anyString(),
                any(SavingsGoalTopUpRequest.class))).thenReturn(responseMock);

        savingsGoalService.addRoundUpsToSavingsGoal("sav0", 2021, 3);
    }

    @Test
    void shouldNotAddRoundUpsToSavingsGoalIfNoneForGivenWeek() {
        AccountsResponse.Account acc0 = new AccountsResponse.Account("id0", "PRIMARY", "cat0", "GBP");
        AccountsResponse accountsResponseMock = new AccountsResponse(Arrays.asList(acc0));

        TransactionFeedItemResponse transactionFeedItemResponseMock = Mockito.mock(TransactionFeedItemResponse.class);

        when(mockStarlingApiClient.getAccounts()).thenReturn(accountsResponseMock);
        when(mockStarlingApiClient.getFeedItemsBetweenTimestamps(anyString(), anyString(), anyMap())).thenReturn(transactionFeedItemResponseMock);

        when(transactionFeedItemResponseMock.getFeedItems()).thenReturn(Collections.emptyList());

        savingsGoalService.addRoundUpsToSavingsGoal("sav0", 2021, 3);
        verify(mockStarlingApiClient, times(0)).addMoneyToSavingsGoal(anyString(), anyString(), anyString(), any(SavingsGoalTopUpRequest.class));
    }

}
