package com.sopra.parkingsystem.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeComponent {
    public static LocalDateTime getCurrentTime() {
        ZoneId stockholmZone = ZoneId.of("Europe/Oslo");
        ZonedDateTime stockholmTime = ZonedDateTime.now(stockholmZone);
        return stockholmTime.toLocalDateTime();
    }
}
