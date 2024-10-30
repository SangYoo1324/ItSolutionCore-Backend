package com.example.ItSolutionCore.businesses.attManager.entity.user.employee;


import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@DiscriminatorValue("employee")
@Table(name="employee")
public class Employee extends User {
    public Employee(Long id, Company company, String firstName, String lastName, String profile_pic, String password, String email, String job, int employeeID) {
        super(id, company, firstName, lastName, profile_pic, password, email);
        this.job = job;
    }

    public Employee(String job) {
        this.job = job;
    }

    public Employee(UserBuilder<?, ?> b, String job, Long employeeID) {
        super(b);
        this.job = job;
        this.employeeID =  employeeID;
    }

    protected String job;
    protected Long employeeID;



    public EmployeeResponseDto toResponseDto(){
        return EmployeeResponseDto.builder()
                .job(this.getJob())
                .employeeID(this.getEmployeeID())
                .company(this.getCompany().toResponseDto())
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .profile_pic(this.getProfile_pic())
                .id(this.getId())
                .build();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "job='" + job + '\'' +
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
