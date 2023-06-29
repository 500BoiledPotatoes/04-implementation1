package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AttractionReview {
    private Long attractionReviewId;
    private Customer customer;
    private String reviewContent;
    private int score;
    private Attraction attraction;


    private LocalDateTime time;



    public AttractionReview() {
    }

    public AttractionReview(LocalDateTime time, int score, Customer customer, String reviewContent, Attraction attraction) {
        this.customer = customer;
        this.time = time;
        this.score = score;
        this.attraction = attraction;
        this.reviewContent = reviewContent;
    }
    @Column(name = "time")
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attractionreview_id", nullable = false)
    public Long getAttractionReviewId() {
        return attractionReviewId;
    }

    public void setAttractionReviewId(Long attractionReviewId) {
        this.attractionReviewId = attractionReviewId;
    }


    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    @Column(name = "review_content", columnDefinition = "text")
    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    @ManyToOne
    @JoinColumn(name = "attraction_attraction_id")
    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }


    @Column(name = "attractionreview_score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
