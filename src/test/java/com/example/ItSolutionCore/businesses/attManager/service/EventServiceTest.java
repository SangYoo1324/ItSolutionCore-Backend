package com.example.ItSolutionCore.businesses.attManager.service;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    @Test
    void postEvent() {
       Long daySelected = 1727074800000L;
        System.out.println("daySelected::"+ new Timestamp(daySelected).toString());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
        try {
            Date date = sdf.parse("Wed Sep 25 2024");
            long timestamp = date.getTime();
            System.out.println("Timestamp:"+timestamp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void timeStampLongValueGenerator(){

        String [] times = {"Wed Sep 27 2024 15:00:00", "Wed Sep 27 2024 20:00:00",
                "Wed Sep 28 2024 18:00:00","Wed Sep 28 2024 22:00:00"};

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
        try {
            for(String time :times){
                Date date = sdf.parse(time);
                long timestamp = date.getTime();
                System.out.println("Timestamp:"+timestamp);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}