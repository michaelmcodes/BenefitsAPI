package com.michaelmark.healthplanenrollmentapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "issuer_name")
    private String issuerName;

    @Column(name = "state", length = 2)
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "metal_level")
    private MetalLevel metalLevel;

    @Column(name = "individual_rate", precision = 10, scale = 2)
    private BigDecimal individualRate;

    @Column(precision = 10, scale = 2)
    private BigDecimal deductible;

    @Column(name = "county")
    private String county;

    @Column(name = "hios_issuer_id")
    private String hiosIssuerId;
}
