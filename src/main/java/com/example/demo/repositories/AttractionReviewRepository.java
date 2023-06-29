package com.example.demo.repositories;

import com.example.demo.model.Attraction;
import com.example.demo.model.AttractionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionReviewRepository extends JpaRepository<AttractionReview,Long> {
    List<AttractionReview>findAllByAttraction(Attraction attraction);

}
