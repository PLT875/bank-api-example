package com.starling.domain.util;

import com.starling.infrastructure.starlingapi.FeedItem;
import com.starling.infrastructure.starlingapi.TransactionFeedItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    public void calculateStartOfWeekWhenMonday() {
        // Mon 8th
        LocalDateTime local = LocalDateTime.of(2021, 3, 8, 21, 33, 47, 223);
        ZonedDateTime startOfWeek = TransactionFeedUtil.calculateStartOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(1, startOfWeek.getDayOfWeek().getValue());
        assertEquals(0, startOfWeek.getHour());
        assertEquals(0, startOfWeek.getMinute());
        assertEquals(0, startOfWeek.getSecond());
        assertEquals(0, startOfWeek.getNano());
        assertEquals("2021-03-08T00:00Z", startOfWeek.toString());
    }

    @Test
    public void calculateStartOfWeekWhenNotMonday() {
        // Wed 10th
        LocalDateTime local = LocalDateTime.of(2021, 3, 10, 21, 33, 47, 223);
        ZonedDateTime startOfWeek = TransactionFeedUtil.calculateStartOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(1, startOfWeek.getDayOfWeek().getValue());
        assertEquals(0, startOfWeek.getHour());
        assertEquals(0, startOfWeek.getMinute());
        assertEquals(0, startOfWeek.getSecond());
        assertEquals(0, startOfWeek.getNano());
        assertEquals("2021-03-08T00:00Z", startOfWeek.toString());
    }

    @Test
    public void calculateEndOfWeekWhenSunday() {
        // Sun 14th
        LocalDateTime local = LocalDateTime.of(2021, 3, 14, 21, 33, 47, 223);
        ZonedDateTime endOfWeek = TransactionFeedUtil.calculateEndOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(7, endOfWeek.getDayOfWeek().getValue());
        assertEquals(23, endOfWeek.getHour());
        assertEquals(59, endOfWeek.getMinute());
        assertEquals(59, endOfWeek.getSecond());
        assertEquals(999_999_999, endOfWeek.getNano());
        assertEquals("2021-03-14T23:59:59.999999999Z", endOfWeek.toString());
    }

    @Test
    public void calculateEndOfWeekWhenNotSunday() {
        // Wed 10th
        LocalDateTime local = LocalDateTime.of(2021, 3, 10, 21, 33, 47, 223);
        ZonedDateTime endOfWeek = TransactionFeedUtil.calculateEndOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(7, endOfWeek.getDayOfWeek().getValue());
        assertEquals(23, endOfWeek.getHour());
        assertEquals(59, endOfWeek.getMinute());
        assertEquals(59, endOfWeek.getSecond());
        assertEquals(999_999_999, endOfWeek.getNano());
        assertEquals("2021-03-14T23:59:59.999999999Z", endOfWeek.toString());
    }
}
