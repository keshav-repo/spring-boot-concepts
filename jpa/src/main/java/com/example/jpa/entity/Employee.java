package com.example.jpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity {
    private String firstName;
    private String lastName;
    @Embedded
    private Address address;

}
