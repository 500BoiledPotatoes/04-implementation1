package com.example.demo.repositories;

import com.example.demo.model.Attraction;
import com.example.demo.model.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    List<Destination>findAllByDestinationNameContaining(String name);

    List<Destination>findAllByDestinationNameCnContaining(String name);
    Destination findByDestinationName(String name);

    Destination findByDestinationNameCn(String name);

    Page<Destination> findAll(Pageable pageable);

    Destination findByDestinationId(Long id);

    Page<Destination> findAllByDestinationNameContaining(String name,Pageable pageable);
}
