package com.example.ItSolutionCore.common.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


class GenericUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(GenericUtilTest.class);
    @Test
    void varTest(){
        String jsTime = "2024-05-09";

        // Changing the String jsTime to Date Object
        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long dateAsLong = dateFormat.parse(jsTime).getTime();


            // Chaning the Date (parsed to long) to TimeStamp
            Timestamp timestampObj = new Timestamp(dateAsLong);

            LocalDateTime dateTime = timestampObj.toLocalDateTime(); logger.info(dateTime.toString());   //2024-10-24T00:00
            LocalDate date = timestampObj.toLocalDateTime().toLocalDate(); logger.info("date::"+ date.toString()); // 2024-10-24

            //요일 Extract
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            logger.info("Day of Week::"+ dayOfWeek); //THURSDAY

            //withDayOfMonth => Chainging the givenDate's day with the param. 2024-10-24  -> 2024-10-1  (only day changed)
            LocalDate firstOfMonth = date.withDayOfMonth(1); logger.info("firstOfMonth"+firstOfMonth.toString());
            LocalDate lastOfMonth = date.withDayOfMonth(date.lengthOfMonth()); logger.info("lastOfMonth"+lastOfMonth.toString());

            // dayOfWeek(ex: MON) but same or after the 1st date of the month (So, Month's first n요일의 date)
            LocalDate firstDateOfDayOftheWeek = firstOfMonth.with(TemporalAdjusters.nextOrSame(dayOfWeek)); logger.info("firstDayOfWeek:"+ firstDateOfDayOftheWeek.toString());

            while(!firstDateOfDayOftheWeek.isAfter(lastOfMonth)){
                logger.info("check:"+ firstDateOfDayOftheWeek.atStartOfDay());
                logger.info("monthly dates::"+ Timestamp.valueOf(firstDateOfDayOftheWeek.atStartOfDay())); // 2024-05-02 00:00:00.0

                // moving 1 week ++
                firstDateOfDayOftheWeek = firstDateOfDayOftheWeek.plusWeeks(1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}