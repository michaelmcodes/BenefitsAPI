package com.michaelmark.healthplanenrollmentapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "employer_id", nullable = false)
    private String employerId;

    @Column(name = "state", length = 2, nullable = false)
    private String state;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdAt;
}
