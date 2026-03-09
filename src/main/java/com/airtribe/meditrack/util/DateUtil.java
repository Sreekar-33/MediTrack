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
    // Generates available time slots for a given date
    public static String[] getAvailableSlots(String date) {
        return new String[]{
                date + " 09:00", date + " 10:00", date + " 11:00",
                date + " 13:00", date + " 14:00", date + " 15:00",
                date + " 16:00", date + " 17:00"
        };
    }
}
