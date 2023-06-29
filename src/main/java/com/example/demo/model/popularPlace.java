package com.example.demo.model;

public class popularPlace {
    int orderNum;
    String destination;
    String name;

    String name_en;
    String imgLink;

    private String city_en;

    @Override
    public String toString() {
        return "popularPlace{" +
                "orderNum=" + orderNum +
                ", destination='" + destination + '\'' +
                ", name='" + name + '\'' +
                ", name_en='" + name_en + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", city_en='" + city_en + '\'' +
                '}';
    }

    public int getOrderNum() {
        return orderNum;
    }

    public String getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getName_en() {
        return name_en;
    }

    public String getCity_en() {
        return city_en;
    }

    public void setCity_en(String city_en) {
        this.city_en = city_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public popularPlace(int orderNum, String destination, String name, String imgLink) {
        this.orderNum = orderNum;
        this.destination = destination;
        this.name = name;
        this.imgLink = imgLink;
    }

    public popularPlace(int orderNum, String destination, String name, String name_en,String city_en, String imgLink) {
        this.orderNum = orderNum;
        this.destination = destination;
        this.name = name;
        this.name_en = name_en;
        this.imgLink = imgLink;
        this.city_en=city_en;
    }

}
