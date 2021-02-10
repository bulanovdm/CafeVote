package com.topjava.CafeVote.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static LocalDateTime getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        return now.truncatedTo(ChronoUnit.SECONDS);
    }

    public static LocalDate getCurrentDate() {
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
        return now;
    }

    public static LocalTime getCurrentTime() {
        LocalTime now = LocalTime.now(Clock.systemDefaultZone());
        return now.truncatedTo(ChronoUnit.SECONDS);
    }

    public static LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDate.parse(str) : null;
    }

    public static LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalTime.parse(str) : null;
    }

    public static LocalDateTime parseLocalDateTime(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDateTime.parse(str) : null;
    }
}
