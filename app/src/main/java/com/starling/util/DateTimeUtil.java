package com.starling.util;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public final class DateTimeUtil {

    private static final long MAX_NANOSECOND = 999_999_999;

    private DateTimeUtil() {
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
