package com.example.demo.repositories;

import com.example.demo.model.Attraction;
import com.example.demo.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findAllByAttractionNameContaining(String name);

    List<Attraction> findAllByAttractionNameCnContaining(String name);

    Attraction findByAttractionName(String name);

    Attraction findByAttractionNameCn(String name);

    Attraction findByAttractionID(Long id);

    List<Attraction>findAllByDestination(Destination destination);

}
