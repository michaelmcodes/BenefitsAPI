package com.michaelmark.healthplanenrollmentapi.repository;

import com.michaelmark.healthplanenrollmentapi.model.MetalLevel;
import com.michaelmark.healthplanenrollmentapi.model.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
//    // Derived query
//    List<Plan> findByState(String state);
//
//    // Derived with multiple filters
//    Page<Plan> findByStateAndMetalLevel(String state, MetalLevel metalLevel, Pageable pageable);
//
//    // JPQL — practice writing it manually
//    @Query("SELECT p FROM Plan p WHERE LOWER(p.planName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.issuerName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Plan> searchByKeyword(@Param("keyword") String keyword);
//
//    // Native SQL — practice this too
//    @Query(value = "SELECT metal_level, state, COUNT(*) as count FROM enrollments e JOIN plans p ON e.plan_id = p.id WHERE e.status = 'ACTIVE' GROUP BY metal_level, state", nativeQuery = true)
//    List<Object[]> getEnrollmentSummary();

}