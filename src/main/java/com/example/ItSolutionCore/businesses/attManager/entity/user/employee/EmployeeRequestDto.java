package com.example.ItSolutionCore.businesses.attManager.entity.user.employee;


import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import com.example.ItSolutionCore.businesses.attManager.entity.user.UserDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class EmployeeDto extends UserDto {
  protected String job;
  protected Long employeeID;

  public EmployeeDto(){

  }


  public EmployeeDto(Long id, Long company_id, String firstName, String lastName, String profile_pic, String password, String email, String job) {
    super(id, company_id, firstName, lastName, profile_pic, password, email);
    this.job = job;
  }

  @Override
  public String toString() {
    return "EmployeeDto{" +
            "job='" + job + '\'' +
            ", id=" + id +
            ", company_id=" + company_id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", profile_pic='" + profile_pic + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
