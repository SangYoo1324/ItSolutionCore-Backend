package com.example.ItSolutionCore.businesses.attManager.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="EMP_TYPE", discriminatorType = DiscriminatorType.STRING)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Long company_id;
    protected String name;
    protected String img_url;
    protected String password;
    protected String email;


}
