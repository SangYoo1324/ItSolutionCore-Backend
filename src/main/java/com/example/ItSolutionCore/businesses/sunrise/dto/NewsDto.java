package com.example.ItSolutionCore.businesses.sunrise.dto;

import com.example.ItSolutionCore.businesses.sunrise.entity.News;
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

    private Timestamp startDate;

    private Timestamp endDate;

    private String time;

    private String dayOfWeek;

    boolean recurring;

    private String cloudinaryUrl;

    private String description;



    public News toNews(){
        return News.builder()
                .id(this.id)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .dayOfWeek(this.dayOfWeek)
                .recurring(this.recurring)
                .cloudinaryUrl(this.cloudinaryUrl)
                .description(this.description)
                .time(this.time)
                .build();
    }
}