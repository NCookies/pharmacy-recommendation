package com.ncookie.pharmacy_recommendation.pharmacy.repository;

import com.ncookie.pharmacy_recommendation.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
