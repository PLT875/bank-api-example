package com.starling.domain.util;

import com.starling.infrastructure.starlingapi.AccountsResponse;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;

import static java.time.temporal.ChronoField.YEAR;

public final class SavingsGoalUtil {

    public static final String FEED_ITEM_OUT = "OUT";

    public static final String PAYMENTS = "PAYMENTS";

    public static final String PRIMARY = "PRIMARY";

    private SavingsGoalUtil() {
    }

    /**
     * assumptions:
     * - there are 48 different spending categories documented, for demo purposes just treating spending as
     * - anything as where direction = OUT and category type = PAYMENT
     *
     * @param transactionFeedItemResponse
     * @return total savings
     */
    public static Integer calculateTotalRoundUpSavings(TransactionFeedItemResponse transactionFeedItemResponse) {
        return transactionFeedItemResponse
                .getFeedItems()
                .stream()
                .filter(f -> FEED_ITEM_OUT.equals(f.getDirection()) && PAYMENTS.equals((f.getSpendingCategory())))
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

    /**
     * @param accountsResponse
     * @return primary account (assuming there is only one primary account)
     */
    public static Optional<AccountsResponse.Account> retrievePrimaryAccount(AccountsResponse accountsResponse) {
        return accountsResponse
                .getAccounts()
                .stream()
                .filter(a -> PRIMARY.equals(a.getAccountType()))
                .findFirst();
    }

    public static ZonedDateTime calculateStartOfWeek(int year, int week) {
        LocalDateTime startOfYearWeek = LocalDateTime.now()
                .with(YEAR, year)
                .with(ChronoField.ALIGNED_WEEK_OF_YEAR, week)
                .with(DayOfWeek.MONDAY)
                .with(ChronoField.HOUR_OF_DAY, 0)
                .with(ChronoField.MINUTE_OF_HOUR, 0)
                .with(ChronoField.SECOND_OF_DAY, 0)
                .with(ChronoField.MILLI_OF_SECOND, 0);

        return ZonedDateTime.of(startOfYearWeek, ZoneOffset.UTC);
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
