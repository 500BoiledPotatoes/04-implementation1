package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Booking {
    private Long bookingId;
    private Destination destination;

    private Customer customer;

    private Accommodation accommodation;

    private Attraction attraction;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isConfirmed;

    private boolean isFinished;


    public Booking() {
    }

    public Booking(Customer customer, Destination destination, Accommodation accommodation, Attraction attraction, LocalDateTime startTime, LocalDateTime endTime) {
        this.destination = destination;
        this.customer = customer;
        this.accommodation = accommodation;
        this.attraction = attraction;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isConfirmed = false;
        this.isFinished = false;
    }

    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    //    private D


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id",nullable = false)
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }


    @ManyToOne
    @JoinColumn(name = "destination_destination_id")
    public Destination getDestination() {
        return destination;
    }


    public void setDestination(Destination destination) {
        this.destination = destination;
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
    @JoinColumn(name = "attraction_attraction_id")
    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    @Column(name = "start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String Start() {
        return startTime.toString().split("T")[0] + " " + startTime.toString().split("T")[1];
    }
    public String End() {
        return endTime.toString().split("T")[0] + " " + endTime.toString().split("T")[1];
    }

    @Column(name = "is_confirmed")
    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
    @Column(name = "is_finished")
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
