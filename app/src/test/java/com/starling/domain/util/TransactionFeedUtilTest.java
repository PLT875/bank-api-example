package com.starling.domain.util;

import com.starling.infrastructure.starlingapi.FeedItem;
import com.starling.infrastructure.starlingapi.TransactionFeedItem;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.starling.domain.util.TransactionFeedUtil.FEED_ITEM_OUT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionFeedUtilTest {

    @Test
    public void calculateTotalRoundUpSavings() {
        FeedItem feedItem0 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(435)));
        FeedItem feedItem1 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(520)));
        FeedItem feedItem2 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(87)));
        FeedItem feedItem3 = new FeedItem("IN", new FeedItem.Amount("GB", Integer.valueOf(50)));
        FeedItem feedItem4 = new FeedItem(FEED_ITEM_OUT, new FeedItem.Amount("GB", Integer.valueOf(100)));

        List<FeedItem> feedItems = Arrays.asList(feedItem0, feedItem1, feedItem2, feedItem3, feedItem4);

        TransactionFeedItem item = new TransactionFeedItem(feedItems);
        assertEquals(258, TransactionFeedUtil.calculateTotalRoundUpSavings(item));
    }

    @Test
    public void calculateTotalRoundUpSavingsWhenNoPayments() {
        FeedItem feedItem0 = new FeedItem("IN", new FeedItem.Amount("GB", Integer.valueOf(50)));
        List<FeedItem> feedItems = Arrays.asList(feedItem0);

        TransactionFeedItem item = new TransactionFeedItem(feedItems);
        assertEquals(0, TransactionFeedUtil.calculateTotalRoundUpSavings(item));

        item = new TransactionFeedItem(Collections.emptyList());
        assertEquals(0, TransactionFeedUtil.calculateTotalRoundUpSavings(item));
    }

    @Test
    public void calculateStartAndEndOfWeek() {
        ZonedDateTime dt0 = TransactionFeedUtil.calculateStartOfWeek(2020, 53);
        assertEquals("2020-12-28T00:00Z", dt0.toString());
        assertEquals("2021-01-03T23:59:59.999999999Z", TransactionFeedUtil.calculateEndOfWeek(dt0));

        ZonedDateTime dt1 = TransactionFeedUtil.calculateStartOfWeek(2021, 10);
        assertEquals("2021-03-08T00:00Z", dt1.toString());
        assertEquals("2021-03-14T23:59:59.999999999Z", TransactionFeedUtil.calculateEndOfWeek(dt1));
    }

}
