package com.example.demo.repositories;

import com.example.demo.model.Accommodation;
import com.example.demo.model.Destination;
import com.example.demo.model.PackageItem;
import com.example.demo.model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageItemRepository extends JpaRepository<PackageItem, Long> {
    List<PackageItem> findAllByTravelPackage(TravelPackage travelPackage);
    PackageItem findByPackageItemId(Long id);


}
