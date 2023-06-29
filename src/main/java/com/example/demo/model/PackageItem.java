package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class PackageItem {
    private Long packageItemId;

    private Destination destination;

    private Accommodation accommodation;

    private Attraction attraction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    private TravelPackage travelPackage;


    public PackageItem() {
    }

    public PackageItem(Destination destination, Accommodation accommodation, Attraction attraction, LocalDateTime startDate, LocalDateTime endDate) {
        this.destination = destination;
        this.accommodation = accommodation;
        this.attraction = attraction;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @ManyToOne
    @JoinColumn(name = "travel_package_travel_package_id")
    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    @Id
    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_item_id", nullable = false)
    public Long getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(Long packageItemId) {
        this.packageItemId = packageItemId;
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
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_time")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
