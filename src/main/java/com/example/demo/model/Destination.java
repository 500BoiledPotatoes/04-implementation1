package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Destination {
    private Long destinationId;
    private String destinationName;

    private String destinationNameCn;
    private String destinationIntro;

    private String destinationIntroCn;

    private String destinationPicPath;
    private List<Attraction> attractions = new ArrayList<>();
    private List<Accommodation> accommodations = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<PackageItem> packageItems = new ArrayList<>();

    public Destination(String destinationName, String destinationIntro) {
        this.destinationName = destinationName;
        this.destinationIntro = destinationIntro;
    }

    public Destination(String destinationName, String destinationIntro, String destinationPicPath) {
        this.destinationName = destinationName;
        this.destinationIntro = destinationIntro;
        this.destinationPicPath = destinationPicPath;
    }

    public Destination(String destinationName, String destinationNameCn,String destinationIntro,String destinationIntroCn, String destinationPicPath) {
        this.destinationName = destinationName;
        this.destinationNameCn = destinationNameCn;
        this.destinationIntro = destinationIntro;
        this.destinationIntroCn = destinationIntroCn;
        this.destinationPicPath = destinationPicPath;
    }

    public Destination() {
    }

    @Column(name = "destination_PicPath")
    public String getDestinationPicPath() {
        return destinationPicPath;
    }

    public void setDestinationPicPath(String destinationPicPath) {
        this.destinationPicPath = destinationPicPath;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id", nullable = false)
    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    @Column(name = "destination_name", unique = true)
    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Column(name = "destination_intro", columnDefinition = "text")
    public String getDestinationIntro() {
        return destinationIntro;
    }

    public void setDestinationIntro(String destinationIntro) {
        this.destinationIntro = destinationIntro;
    }


    @Column(name = "destination_name_Cn", unique = true)
    public String getDestinationNameCn() {
        return destinationNameCn;
    }

    public void setDestinationNameCn(String destinationNameCn) {
        this.destinationNameCn = destinationNameCn;
    }

    @Column(name = "destination_intro_Cn", columnDefinition = "text")
    public String getDestinationIntroCn() {
        return destinationIntroCn;
    }

    public void setDestinationIntroCn(String destinationIntroCn) {
        this.destinationIntroCn = destinationIntroCn;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public void addAccommodations(Accommodation accommodation) {
        this.accommodations.add(accommodation);
    }

    public void addAttractions(Attraction attraction) {
        this.attractions.add(attraction);
    }


}
