package com.example.ItSolutionCore.businesses.sunrise.data.dto;

import com.example.ItSolutionCore.businesses.sunrise.data.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class NewsDto {


    private Long id;

    private String title;

    private Long startDate;

    private Long endDate;

    private String time;

    private String dayOfWeek;

    boolean recurring;

    private String s3_url;

    private String description;


}