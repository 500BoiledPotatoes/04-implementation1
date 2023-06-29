package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccommodationReview {
    private Long accommodationReviewId;

    private long score;
    private Customer customer;
    private Accommodation accommodation;
    private String reviewContent;

    private LocalDateTime time;

    @Column(name = "time")
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public AccommodationReview(LocalDateTime time, Customer customer, String reviewContent, int score, Accommodation accommodation) {
        this.customer = customer;
        this.time = time;
        this.reviewContent = reviewContent;
        this.score = score;
        this.accommodation = accommodation;
    }

    public AccommodationReview() {
    }

    @Override
    public String toString() {
        return "AccommodationReview{" +
                "accommodationReviewId=" + accommodationReviewId +
                ", score=" + score +
                ", customer=" + customer +
                ", accommodation=" + accommodation +
                ", reviewContent='" + reviewContent + '\'' +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "accommodation_accommodation_id")
    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accommodationreview_id")
    public Long getAccommodationReviewId() {
        return accommodationReviewId;
    }

    public void setAccommodationReviewId(Long accommodationReviewId) {
        this.accommodationReviewId = accommodationReviewId;
    }

    @Column(name = "accommodationreview_score")
    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Column(name = "review_content", columnDefinition = "text")
    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
