package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Attraction {
    private Long attractionID;
    private String address;

    private String addressCn;
    private String attractionName;
    private String attractionNameCn;
    private String attractionIntro;
    private String attractionIntroCn;
    private Destination destination;
    private List<AttractionReview> attractionReviews = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    private Integer score;

    private Integer CommentNum;

    private Integer tempSum;



    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    private String ImagePath;
    private List<PackageItem> packageItems = new ArrayList<>();
    public Attraction(String attractionName, String attractionIntro, String address, Destination destination) {
        this.destination = destination;
        this.attractionName = attractionName;
        this.attractionIntro = attractionIntro;
        this.address = address;
    }

    public Attraction(String attractionName, String attractionNameCn,String attractionIntro,String attractionIntroCn, String address,String addressCn, Destination destination,String imagePath) {
        this.destination = destination;
        this.attractionName = attractionName;
        this.attractionNameCn = attractionNameCn;
        this.attractionIntro = attractionIntro;
        this.attractionIntroCn = attractionIntroCn;
        this.address = address;
        this.addressCn = addressCn;
        this.ImagePath=imagePath;
        this.tempSum=0;
        this.score=90;
        this.CommentNum=0;
    }

    public Attraction() {
    }

    @Column(name = "attraction_Score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Column(name = "comment_Num")
    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    @ManyToOne
    @JoinColumn(name = "destination_destination_id")
    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id", nullable = false)
    public Long getAttractionID() {
        return attractionID;
    }

    public void setAttractionID(Long attractionID) {
        this.attractionID = attractionID;
    }

    @Column(name = "attraction_name", unique = true)
    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    @Column(name = "attraction_intro", columnDefinition = "text")
    public String getAttractionIntro() {
        return attractionIntro;
    }

    public void setAttractionIntro(String attractionIntro) {
        this.attractionIntro = attractionIntro;
    }

    @Column(name = "attraction_address_Cn", unique = true)
    public String getAddressCn() {
        return addressCn;
    }

    public void setAddressCn(String addressCn) {
        this.addressCn = addressCn;
    }

    @Column(name = "attraction_name_Cn")
    public String getAttractionNameCn() {
        return attractionNameCn;
    }

    public void setAttractionNameCn(String attractionNameCn) {
        this.attractionNameCn = attractionNameCn;
    }

    @Column(name = "attraction_intro_Cn", columnDefinition = "text")
    public String getAttractionIntroCn() {
        return attractionIntroCn;
    }

    public void setAttractionIntroCn(String attractionIntroCn) {
        this.attractionIntroCn = attractionIntroCn;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<AttractionReview> getAttractionReviews() {
        return attractionReviews;
    }

    public void setAttractionReviews(List<AttractionReview> attractionReviews) {
        this.attractionReviews = attractionReviews;
    }
    @OneToMany(cascade = CascadeType.ALL)
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    @Column(name = "attraction_address")

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addReview(AttractionReview a){
        this.attractionReviews.add(a);
    }

    @Column(name = "Temp_Sum")
    public int getTempSum() {
        return tempSum;
    }

    public void setTempSum(int tempSum) {
        this.tempSum = tempSum;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "attractionID=" + attractionID +
                ", address='" + address + '\'' +
                ", addressCn='" + addressCn + '\'' +
                ", attractionName='" + attractionName + '\'' +
                ", attractionNameCn='" + attractionNameCn + '\'' +
                ", attractionIntro='" + attractionIntro + '\'' +
                ", attractionIntroCn='" + attractionIntroCn + '\'' +
                ", destination=" + destination +
                ", attractionReviews=" + attractionReviews +
                ", bookings=" + bookings +
                ", packageItems=" + packageItems +
                '}';
    }
}
