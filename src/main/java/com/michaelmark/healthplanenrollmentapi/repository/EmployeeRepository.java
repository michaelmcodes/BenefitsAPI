package com.michaelmark.healthplanenrollmentapi.repository;

import com.michaelmark.healthplanenrollmentapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
