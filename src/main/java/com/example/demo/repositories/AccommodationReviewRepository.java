package com.example.demo.repositories;

import com.example.demo.model.Accommodation;
import com.example.demo.model.AccommodationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview,Long> {
    List<AccommodationReview>findAllByAccommodation(Accommodation accommodation);

}
