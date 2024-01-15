package com.example.demo11.repository;

import com.example.demo11.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Integer> {

    List<Lead> findByMobileNumber(String mobileNumber);

}
