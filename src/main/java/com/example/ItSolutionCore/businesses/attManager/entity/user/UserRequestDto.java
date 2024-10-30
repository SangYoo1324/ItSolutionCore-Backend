package com.example.ItSolutionCore.businesses.attManager.entity.user;


import com.example.ItSolutionCore.businesses.attManager.entity.event.EventRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
public class UserDto {

    protected Long id;

    protected Long company_id;

    protected String firstName;

    protected String lastName;

    protected String profile_pic;

    protected String password;

    protected String email;

    protected List<EventRequestDto> events;

    public UserDto(){

    }


    public UserDto(Long id, Long company_id, String firstName, String lastName, String profile_pic, String password, String email) {
        this.id = id;
        this.company_id = company_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile_pic = profile_pic;
        this.password = password;
        this.email = email;
    }
}
