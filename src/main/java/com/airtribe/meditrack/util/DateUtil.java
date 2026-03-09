package com.airtribe.meditrack.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String format(LocalDateTime dateTime) {

        return dateTime.format(formatter);

    }

    public static LocalDateTime parse(String dateString) {

        return LocalDateTime.parse(dateString, formatter);

    }

    public static LocalDateTime now() {

        return LocalDateTime.now();

    }
}
