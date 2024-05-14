package com.example.ItSolutionCore.common.uni_dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class PublicFileDto {

    private Long id;

    private String fileName;

    // s3 is object file storage. Every object stored with key, value pair. Key contains the Business, Category info
    private String filePath;

    private String s3_url;

    private String contentType;

    private Long size;

    private Timestamp registeredDate;

    private String business;

}
