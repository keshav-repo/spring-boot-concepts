package org.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {
    public static LocalDateTime parseDate(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime, formatter);
    }
}
