package com.example.ItSolutionCore.businesses.sunrise.data.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@Slf4j
public class SermonRequestDto {
    private Long id;
    private String iframe;
    private String title;
    private String date;
    private String scripture;
}
