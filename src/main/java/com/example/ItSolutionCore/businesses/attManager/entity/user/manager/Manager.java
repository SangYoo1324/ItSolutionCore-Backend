package com.example.ItSolutionCore.businesses.attManager.entity.user.manager;

import com.example.ItSolutionCore.businesses.attManager.entity.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("manager")
@Table(name="manager")
@SuperBuilder
@Getter
@Setter
public class Manager extends User {
    protected Long admin_code;
}
