package com.example.ItSolutionCore.businesses.attManager.entity.user;

import com.example.ItSolutionCore.businesses.attManager.entity.company.CompanyResponseDto;
import com.example.ItSolutionCore.businesses.attManager.entity.event.EventRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    protected Long id;

    protected CompanyResponseDto company;

    protected String firstName;

    protected String lastName;

    protected String profile_pic;

    protected String password;

    protected String email;

//    protected List<EventRequestDto> events;




}
