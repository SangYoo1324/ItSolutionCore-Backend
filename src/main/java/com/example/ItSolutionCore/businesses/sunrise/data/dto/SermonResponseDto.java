package com.example.ItSolutionCore.businesses.sunrise.data.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Builder
@Data
@Slf4j
public class SermonResponseDto {
    private Long id;
    private String iframe;
    private String title;
    private Long date;
    private String scripture;
}
