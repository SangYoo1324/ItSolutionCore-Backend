package com.example.ItSolutionCore.businesses.attManager.entity.user.employee;


import com.example.ItSolutionCore.businesses.attManager.entity.user.UserRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class EmployeeRequestDto extends UserRequestDto {
  protected String job;
  protected Long employeeID;

  public EmployeeRequestDto(){

  }


  public EmployeeRequestDto(Long id, Long company_id, String firstName, String lastName, String profile_pic, String password, String email, String job) {
    super(id, company_id, firstName, lastName, profile_pic, password, email);
    this.job = job;
  }

  @Override
  public String toString() {
    return "EmployeeRequestDto{" +
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
