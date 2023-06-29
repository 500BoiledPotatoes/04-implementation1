package com.example.demo.model;

import javax.persistence.*;

@Entity
public class AccommodationComment {
    private Long aCommentId;
    private String title;
    private String content;
    private Customer customer;

    private Accommodation accommodation;

    public AccommodationComment(String title, String content, Customer customer, Accommodation accommodation) {
        this.title = title;
        this.content = content;
        this.customer = customer;
        this.accommodation = accommodation;
    }

    public AccommodationComment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accommodationcomment_id")
    public Long getaCommentId() {
        return aCommentId;
    }

    public void setaCommentId(Long aCommentId) {
        this.aCommentId = aCommentId;
    }

    @Column(name = "accommodationcomment_title")
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "accommodationcomment_content", columnDefinition = "text")
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "accommodation_accommodation_id")
    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        return "AccommodationComment{" +
                "aCommentId=" + aCommentId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", customer=" + customer +
                ", accommodation=" + accommodation +
                '}';
    }
}
