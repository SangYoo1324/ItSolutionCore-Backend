package com.example.ItSolutionCore.businesses.sunrise.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Builder
@Data
@Slf4j
public class SermonDto {
    private Long id;
    private String iframe;
    private String title;
    private Timestamp date;
    private String scripture;
}
