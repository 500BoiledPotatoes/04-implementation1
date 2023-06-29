package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    // this class is mainly in charge of
    // storing the basic information of customer

    private Long customerId;
    // the customer id

    public String name;
    // the customer name

    private String phoneNumber;
    // the phone number of this customer

    private String email;
    // the email address of this customer

    private String pwd;
    // the password of this customer

    private List<AttractionReview> attractionReviews=new ArrayList<>();

    private List<AccommodationReview> accommodationReviews=new ArrayList<>();


    private List<Booking> bookings = new ArrayList<>();

    private List<Traffic> traffics = new ArrayList<>();
//
//    private List<TrafficBooking> trafficBookings =new ArrayList<>();
    public Customer(String name, String phoneNumber, String email, String pwd) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pwd = pwd;
    }

    public Customer() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Id
    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<AttractionReview> getAttractionReviews() {
        return attractionReviews;
    }

    public void setAttractionReviews(List<AttractionReview> attractionReviews) {
        this.attractionReviews = attractionReviews;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<AccommodationReview> getAccommodationReviews() {
        return accommodationReviews;
    }

    public void setAccommodationReviews(List<AccommodationReview> accommodationReviews) {
        this.accommodationReviews = accommodationReviews;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void addAccReview(AccommodationReview a){
        this.accommodationReviews.add(a);
    }
    public void addAttReview(AttractionReview a){
        this.attractionReviews.add(a);
    }

//    @OneToMany(cascade = CascadeType.ALL)
//    public List<TrafficBooking> getTrafficBookings() {
//        return trafficBookings;
//    }
//
//    public void setTrafficBookings(List<TrafficBooking> trafficBookings) {
//        this.trafficBookings = trafficBookings;
//    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Traffic> getTraffics() {
        return traffics;
    }

    public void setTraffics(List<Traffic> traffics) {
        this.traffics = traffics;
    }

    public void addTraffic(Traffic traffic){
        this.traffics.add(traffic);
    }

    public void deleteTraffic(Traffic traffic){
        this.traffics.remove(traffic);
    }
}
