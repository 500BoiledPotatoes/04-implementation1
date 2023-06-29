package com.example.demo.repositories;

import com.example.demo.model.Accommodation;
import com.example.demo.model.Destination;
import com.example.demo.model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    List<TravelPackage> findAllByNameContaining(String name);
    TravelPackage findByPackageId(Long id);
}
