package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Accommodation {
    private Long accommodationId;
    private String accommodationName;

    private String accommodationNameCn;
    private String accommodationIntro;

    private String accommodationIntroCn;
    private String accommodationAddress;

    private String accommodationAddressCn;

    private List<Booking> bookings = new ArrayList<>();
    private Destination destination;

    private String filepath;

    private Integer score;

    private String threeJsPath;

    private Integer CommentNum;

    private Integer tempSum;
    private List<PackageItem> packageItems = new ArrayList<>();

    private List<AccommodationReview> accommodationReviews = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "destination_destination_id")
    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Accommodation(String accommodationName,  String accommodationIntro, String accommodationAddress, Destination destination,String filePath) {
        this.accommodationName = accommodationName;
        this.accommodationIntro = accommodationIntro;
        this.accommodationAddress = accommodationAddress;
        this.destination = destination;
        this.filepath=filePath;
    }

    public Accommodation(String accommodationName, String accommodationNameCn, String accommodationIntro,String accommodationIntroCn, String accommodationAddress,String accommodationAddressCn, Destination destination,String filePath) {
        this.accommodationName = accommodationName;
        this.accommodationNameCn = accommodationNameCn;
        this.accommodationIntro = accommodationIntro;
        this.accommodationIntroCn = accommodationIntroCn;
        this.accommodationAddress = accommodationAddress;
        this.accommodationAddressCn = accommodationAddressCn;
        this.destination = destination;
        this.filepath=filePath;
        this.score=90;
        this.tempSum=0;
        this.CommentNum=0;
    }

    public Accommodation(String accommodationName, String accommodationNameCn, String accommodationIntro, String accommodationIntroCn, String accommodationAddress, String accommodationAddressCn, Destination destination, String filepath, Integer score, String threeJsPath, Integer commentNum, Integer tempSum) {
        this.accommodationName = accommodationName;
        this.accommodationNameCn = accommodationNameCn;
        this.accommodationIntro = accommodationIntro;
        this.accommodationIntroCn = accommodationIntroCn;
        this.accommodationAddress = accommodationAddress;
        this.accommodationAddressCn = accommodationAddressCn;
        this.destination = destination;
        this.filepath = filepath;
        this.score=90;
        this.tempSum=0;
        this.CommentNum=0;
        this.threeJsPath = threeJsPath;
    }

    public Accommodation() {
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
    @Column(name = "accommodation_id", nullable = false)
    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Column(name = "accommodation_name", unique = true)
    public String getAccommodationName() {
        return accommodationName;
    }

    @Column(name = "accommodation_name_cn", unique = true)
    public String getAccommodationNameCn() {
        return accommodationNameCn;
    }

    public void setAccommodationNameCn(String accommodationNameCn) {
        this.accommodationNameCn = accommodationNameCn;
    }

    @Column(name = "accommodation_intro_Cn", columnDefinition = "text")
    public String getAccommodationIntroCn() {
        return accommodationIntroCn;
    }

    public void setAccommodationIntroCn(String accommodationIntroCn) {
        this.accommodationIntroCn = accommodationIntroCn;
    }

    @Column(name = "accommodation_address_Cn")
    public String getAccommodationAddressCn() {
        return accommodationAddressCn;
    }

    public void setAccommodationAddressCn(String accommodationAddressCn) {
        this.accommodationAddressCn = accommodationAddressCn;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    @Column(name = "accommodation_intro", columnDefinition = "text")
    public String getAccommodationIntro() {
        return accommodationIntro;
    }

    public void setAccommodationIntro(String accommodationIntro) {
        this.accommodationIntro = accommodationIntro;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<AccommodationReview> getAccommodationReviews() {
        return accommodationReviews;
    }

    public void setAccommodationReviews(List<AccommodationReview> accommodationReviews) {
        this.accommodationReviews = accommodationReviews;
    }

    @Column(name = "accommodation_address")
    public String getAccommodationAddress() {
        return accommodationAddress;
    }

    public void setAccommodationAddress(String accommodationAddress) {
        this.accommodationAddress = accommodationAddress;
    }

    @Column(name = "image_path")
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void addReview(AccommodationReview a){
        this.accommodationReviews.add(a);
    }

    @Column(name = "acc_score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "comment_Num")
    public Integer getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(Integer commentNum) {
        CommentNum = commentNum;
    }

    @Column(name = "tempSum")
    public Integer getTempSum() {
        return tempSum;
    }

    public void setTempSum(Integer tempSum) {
        this.tempSum = tempSum;
    }

    @Column(name = "threeJs_path")
    public String getThreeJsPath() {
        return threeJsPath;
    }

    public void setThreeJsPath(String threeJsPath) {
        this.threeJsPath = threeJsPath;
    }
}

