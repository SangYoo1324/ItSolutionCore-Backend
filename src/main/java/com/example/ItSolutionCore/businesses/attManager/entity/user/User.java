package com.example.ItSolutionCore.businesses.attManager.entity.user;

import com.example.ItSolutionCore.businesses.attManager.entity.company.Company;
import com.example.ItSolutionCore.businesses.attManager.entity.event.Event;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

//SuperClass of all types of users
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_CAT", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Entity
@Table(name="user")
@SuperBuilder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="company_id", referencedColumnName = "id", nullable = false)
    protected Company company;
    protected String firstName;
    protected String lastName;
    protected String profile_pic;
    protected String password;
    protected String email;

//    @OneToMany(mappedBy = "user", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
//    protected List<Event> events;

    public User(Long id, Company company, String firstName, String lastName, String profile_pic, String password, String email) {
        this.id = id;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile_pic = profile_pic;
        this.password = password;
        this.email = email;
    }

    public UserResponseDto toResponseDto(){
        return UserResponseDto.builder()
                .id(this.id)
                .company(this.getCompany().toResponseDto())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .profile_pic(this.getProfile_pic())
                .password(this.getPassword())
                .email(this.getEmail())
                .build();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", company=" + company +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
