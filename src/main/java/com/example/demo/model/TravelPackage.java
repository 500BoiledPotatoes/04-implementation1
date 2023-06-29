package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelPackage {
    private Long packageId;

    private String name;
    private String nameCh;
    private List<PackageItem> items = new ArrayList<>();

    private String imgPath;
    public TravelPackage(List<PackageItem> items) {
        this.items = items;
    }

    private String price;
    public TravelPackage() {
    }

    public TravelPackage(String name, String nameCh, String price) {
        this.name = name;
        this.price = price;
        this.nameCh = nameCh;
    }

    public TravelPackage(String name, String nameCh, String imgPath, String price) {
        this.name = name;
        this.nameCh = nameCh;
        this.imgPath = imgPath;
        this.price = price;
    }

    @Id
    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_package_id", nullable = false)
    public Long getPackageId() {
        return packageId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<PackageItem> getItems() {
        return items;
    }

    public void setItems(List<PackageItem> items) {
        this.items = items;
    }

    public void addItem(PackageItem item) {
        this.items.add(item);
    }

    @Column(name = "name_ch")
    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }
    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "img_path")
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
