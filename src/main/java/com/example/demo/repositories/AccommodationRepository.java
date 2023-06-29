package com.example.demo.repositories;

import com.example.demo.model.Accommodation;
import com.example.demo.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findAllByAccommodationNameContaining(String name);

    List<Accommodation> findAllByAccommodationNameCnContaining(String name);

    Accommodation findByAccommodationName(String name);

    Accommodation findByAccommodationNameCn(String name);

    Accommodation findByAccommodationId(Long id);

    List<Accommodation>findAllByDestination(Destination destination);
}
