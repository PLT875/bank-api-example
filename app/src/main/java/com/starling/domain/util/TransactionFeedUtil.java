package com.starling.domain.util;

import com.starling.domain.response.TransactionFeedItem;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public final class TransactionFeedUtil {

    public static final String FEED_ITEM_OUT = "OUT";

    private static final long MAX_NANOSECOND = 999_999_999;

    private TransactionFeedUtil() {
    }

    public static Integer calculateTotalRoundUpSavings(TransactionFeedItem transactionFeedItem) {
        return transactionFeedItem
                .getFeedItems()
                .stream()
                .filter(f -> FEED_ITEM_OUT.equals(f.getDirection()))
                .map(f -> calculateSaving(f.getAmount().getMinorUnits()))
                .reduce(0, Integer::sum);
    }

    /**
     * @param amount
     * @return difference of given amount and its value rounded up to the next 100
     */
    private static Integer calculateSaving(Integer amount) {
        return (((amount / 100) * 100) + 100) - amount;
    }

    public static ZonedDateTime getStartOfWeek(ZonedDateTime now) {
        ZonedDateTime start = now
                .minusNanos(now.getNano())
                .minusHours(now.getHour())
                .minusMinutes(now.getMinute())
                .minusSeconds(now.getSecond());

        if (!DayOfWeek.MONDAY.equals(start.getDayOfWeek())) {
            return start.minusDays(start.getDayOfWeek().getValue() - 1);
        }

        return start;
    }

    public static ZonedDateTime getEndOfWeek(ZonedDateTime now) {
        ZonedDateTime end = now
                .plusNanos(MAX_NANOSECOND - now.getNano())
                .plusHours(23 - now.getHour())
                .plusMinutes(59 - now.getMinute())
                .plusSeconds(59 - now.getSecond());

        if (!DayOfWeek.SUNDAY.equals(end.getDayOfWeek())) {
            return end.plusDays(7 - end.getDayOfWeek().getValue());
        }

        return end;
    }
}
