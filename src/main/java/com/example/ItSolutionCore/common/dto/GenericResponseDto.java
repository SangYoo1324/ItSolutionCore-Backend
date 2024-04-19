package com.example.ItSolutionCore.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenericResponseDto {
    private String response;
    private String error;
    private int errorCode;
}
