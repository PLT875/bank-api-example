package com.starling.domain.util;

import com.starling.infrastructure.starlingapi.TransactionFeedItem;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;

public final class TransactionFeedUtil {

    public static final String FEED_ITEM_OUT = "OUT";

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

    public static ZonedDateTime calculateStartOfWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();

        // n.b. Sun is 1 in gregorian calendar
        cal.setWeekDate(year, week, 2);

        LocalDateTime start = LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);

        return ZonedDateTime.of(start, ZoneOffset.UTC);
    }

    public static String calculateEndOfWeek(ZonedDateTime dateTime) {
        return dateTime
                .plusDays(6)
                .plusHours(23)
                .plusMinutes(59)
                .plusSeconds(59)
                .plusNanos(999_999_999)
                .toString();
    }
}
