package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Traffic {

    private Long trafficId;

    private String type;

    private String typeCn;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String fromPlace;

    private String toPlace;

    private String fromPlaceCn;

    private String toPlaceCn;

    private String price;

    private List<Customer> customers = new ArrayList<>();

    public Traffic(){

    }

    public Traffic(String type, String typeCn, LocalDateTime startDate, LocalDateTime endDate, String fromPlace, String toPlace, String fromPlaceCn, String toPlaceCn, String price) {
        this.type = type;
        this.typeCn = typeCn;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.fromPlaceCn = fromPlaceCn;
        this.toPlaceCn = toPlaceCn;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traffic_id", nullable = false)
    public Long getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(Long id) {
        this.trafficId = id;
    }
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "start_date")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime start) {
        this.startDate = start;
    }
    @Column(name = "end_date")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime end) {
        this.endDate = end;
    }
    @Column(name = "from_place")
    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String from) {
        this.fromPlace = from;
    }
    @Column(name = "to_place")
    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String to) {
        this.toPlace = to;
    }

    @ManyToMany(mappedBy = "traffics")
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    @Column(name = "type_cn")
    public String getTypeCn() {
        return typeCn;
    }

    public void setTypeCn(String typeCn) {
        this.typeCn = typeCn;
    }
    @Column(name = "from_place_cn")
    public String getFromPlaceCn() {
        return fromPlaceCn;
    }

    public void setFromPlaceCn(String fromPlaceCn) {
        this.fromPlaceCn = fromPlaceCn;
    }
    @Column(name = "to_place_cn")
    public String getToPlaceCn() {
        return toPlaceCn;
    }

    public void setToPlaceCn(String toPlaceCn) {
        this.toPlaceCn = toPlaceCn;
    }

    public void deleteCus(Customer customer){
        this.customers.remove(customer);
    }
}
