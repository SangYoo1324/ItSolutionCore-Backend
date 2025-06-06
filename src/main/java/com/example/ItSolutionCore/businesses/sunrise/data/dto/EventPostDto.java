package com.example.ItSolutionCore.businesses.sunrise.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EventPostDto {
    private Long id;

    private String title;

//    private String date;
//
    private String time;

    private Long date;

    private String s3_url;

    private String description;

}
