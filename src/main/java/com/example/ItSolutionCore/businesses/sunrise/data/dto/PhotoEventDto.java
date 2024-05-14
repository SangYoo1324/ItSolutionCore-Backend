package com.example.ItSolutionCore.businesses.sunrise.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PhotoEventDto {
    private Long id;
    private String title;
    private String subTitle;
    private List<String> s3_urls;

}
