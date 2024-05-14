package com.example.ItSolutionCore.businesses.sunrise.dto;

import com.example.ItSolutionCore.businesses.sunrise.entity.PhotoEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PhotoEventDto {

    private String title;
    private String subTitle;
    private List<String> s3_urls;

}
