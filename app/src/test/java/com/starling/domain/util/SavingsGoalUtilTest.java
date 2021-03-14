package com.starling.domain.util;

import com.starling.infrastructure.starlingapi.AccountsResponse;
import com.starling.infrastructure.starlingapi.FeedItem;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.starling.domain.util.SavingsGoalUtil.FEED_ITEM_OUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsGoalUtilTest {

    @Test
    void retrievePrimaryAccount() {
        AccountsResponse.Account acc0 = new AccountsResponse.Account("id0", "PRIMARY", "cat0", "GBP");
        AccountsResponse.Account acc1 = new AccountsResponse.Account("id1", "SECONDARY", "cat1", "GBP");
        AccountsResponse accountsResponse = new AccountsResponse(Arrays.asList(acc0, acc1));
        Optional<AccountsResponse.Account> account = SavingsGoalUtil.retrievePrimaryAccount(accountsResponse);

        assertTrue(account.isPresent());
        assertEquals("id0", account.get().getAccountUid());
        assertEquals("PRIMARY", account.get().getAccountType());
    }

    @Test
    void calculateTotalRoundUpSavings() {
        FeedItem feedItem0 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(435)));
        FeedItem feedItem1 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(520)));
        FeedItem feedItem2 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(87)));
        FeedItem feedItem3 = new FeedItem("IN", new FeedItem.Amount("GB", Integer.valueOf(50)));
        FeedItem feedItem4 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(100)));

        List<FeedItem> feedItems = Arrays.asList(feedItem0, feedItem1, feedItem2, feedItem3, feedItem4);

        TransactionFeedItemResponse item = new TransactionFeedItemResponse(feedItems);
        assertEquals(258, SavingsGoalUtil.calculateTotalRoundUpSavings(item));
    }

    @Test
    void calculateTotalRoundUpSavingsWhenNoPayments() {
        FeedItem feedItem0 = new FeedItem("IN", new FeedItem.Amount("GB", Integer.valueOf(50)));
        List<FeedItem> feedItems = Arrays.asList(feedItem0);

        TransactionFeedItemResponse item = new TransactionFeedItemResponse(feedItems);
        assertEquals(0, SavingsGoalUtil.calculateTotalRoundUpSavings(item));

        item = new TransactionFeedItemResponse(Collections.emptyList());
        assertEquals(0, SavingsGoalUtil.calculateTotalRoundUpSavings(item));
    }

    @Test
    void calculateStartAndEndOfWeek() {
        ZonedDateTime dt0 = SavingsGoalUtil.calculateStartOfWeek(2020, 53);
        assertEquals("2020-12-28T00:00Z", dt0.toString());
        assertEquals("2021-01-03T23:59:59.999999999Z", SavingsGoalUtil.calculateEndOfWeek(dt0));

        ZonedDateTime dt1 = SavingsGoalUtil.calculateStartOfWeek(2021, 10);
        assertEquals("2021-03-08T00:00Z", dt1.toString());
        assertEquals("2021-03-14T23:59:59.999999999Z", SavingsGoalUtil.calculateEndOfWeek(dt1));
    }

}
