package com.starling.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilTest {

    @Test
    public void getStartOfWeekWhenMonday() {
        // Mon 8th
        LocalDateTime local = LocalDateTime.of(2021, 3, 8, 21, 33, 47, 223);
        ZonedDateTime startOfWeek = DateTimeUtil.getStartOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(1, startOfWeek.getDayOfWeek().getValue());
        assertEquals(0, startOfWeek.getHour());
        assertEquals(0, startOfWeek.getMinute());
        assertEquals(0, startOfWeek.getSecond());
        assertEquals(0, startOfWeek.getNano());
    }

    @Test
    public void getStartOfWeekWhenNotMonday() {
        // Wed 10th
        LocalDateTime local = LocalDateTime.of(2021, 3, 10, 21, 33, 47, 223);
        ZonedDateTime startOfWeek = DateTimeUtil.getStartOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(1, startOfWeek.getDayOfWeek().getValue());
        assertEquals(0, startOfWeek.getHour());
        assertEquals(0, startOfWeek.getMinute());
        assertEquals(0, startOfWeek.getSecond());
        assertEquals(0, startOfWeek.getNano());
    }

    @Test
    public void getEndOfWeekWhenSunday() {
        // Sun 14th
        LocalDateTime local = LocalDateTime.of(2021, 3, 14, 21, 33, 47, 223);
        ZonedDateTime endOfWeek = DateTimeUtil.getEndOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(7, endOfWeek.getDayOfWeek().getValue());
        assertEquals(23, endOfWeek.getHour());
        assertEquals(59, endOfWeek.getMinute());
        assertEquals(59, endOfWeek.getSecond());
        assertEquals(999_999_999, endOfWeek.getNano());
    }

    @Test
    public void getEndOfWeekWhenNotSunday() {
        // Wed 10th
        LocalDateTime local = LocalDateTime.of(2021, 3, 10, 21, 33, 47, 223);
        ZonedDateTime endOfWeek = DateTimeUtil.getEndOfWeek(ZonedDateTime.of(local, ZoneOffset.UTC));

        assertEquals(7, endOfWeek.getDayOfWeek().getValue());
        assertEquals(23, endOfWeek.getHour());
        assertEquals(59, endOfWeek.getMinute());
        assertEquals(59, endOfWeek.getSecond());
        assertEquals(999_999_999, endOfWeek.getNano());
    }
}
