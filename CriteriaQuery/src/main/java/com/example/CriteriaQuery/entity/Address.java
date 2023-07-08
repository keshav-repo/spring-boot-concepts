package com.example.CriteriaQuery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", unique = true)
    private Long employeeId;

    private String city;
    private String country;

//    @JsonIgnore
//    @OneToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", insertable = false, updatable = false)
//    private Employee employee;

}
