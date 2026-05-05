package com.michaelmark.healthplanenrollmentapi.repository;

import com.michaelmark.healthplanenrollmentapi.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {


}
