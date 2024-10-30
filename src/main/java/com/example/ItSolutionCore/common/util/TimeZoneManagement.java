package com.example.ItSolutionCore.common.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneManagement {

    // UTC 시간을 특정 타임존의 시간으로 변환하는 유틸리티 메서드   // "2024-09-20T10:15:30Z";  // ISO 8601 형식 UTC 시간
    public static String convertToTimezone(String utcTime, String targetTimezone) {
        // UTC 시간을 기준으로 ZonedDateTime 객체 생성
        ZonedDateTime utcDateTime = ZonedDateTime.parse(utcTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // 타겟 타임존으로 ZonedDateTime 변환
        ZonedDateTime targetDateTime = utcDateTime.withZoneSameInstant(ZoneId.of(targetTimezone));

        // 결과를 포맷팅해서 반환
        return targetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
    }

    // Timestamp를 특정 타임존의 시간으로 변환하는 메서드
    public static String convertTimestampToTimezone(Timestamp timestamp, String targetTimezone) {
        // Timestamp를 Instant로 변환
        Instant instant = timestamp.toInstant();

        // Instant를 기준으로 ZonedDateTime을 생성하고, 타겟 타임존으로 변환
        ZonedDateTime targetDateTime = instant.atZone(ZoneId.of(targetTimezone));

        // 결과를 포맷팅해서 반환 (예: yyyy-MM-dd HH:mm:ss z)
        return targetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
    }
}
