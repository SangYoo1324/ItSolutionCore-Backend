package com.example.ItSolutionCore.businesses.attManager.entity.user.employee;

import com.example.ItSolutionCore.businesses.attManager.entity.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto extends UserResponseDto {
    protected String job;
    protected Long employeeID;

    @Override
    public String toString() {
        return "EmployeeResponseDto{" +
                "job='" + job + '\'' +
                ", employeeID=" + employeeID +
                ", id=" + id +
                ", company=" + company +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
