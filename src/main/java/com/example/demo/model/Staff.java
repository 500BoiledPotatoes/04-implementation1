package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Staff {
    // this class is mainly in charge of
    // storing the basic information of customer

    private Long staffId;
    // the customer id

    private String name;
    // the customer name

    private String phoneNumber;
    // the phone number of this customer

    private String email;
    // the email address of this customer

    private String pwd;
    // the password of this customer


    public Staff(String name, String phoneNumber, String email, String pwd) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pwd = pwd;
    }

    public Staff() {
    }

    @Id
    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long customerId) {
        this.staffId = customerId;
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
}
