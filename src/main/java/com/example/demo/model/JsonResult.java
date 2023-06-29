package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class JsonResult {
	
	private int state;
	
	private String message;

	private Staff staff;

	private Traffic traffic;
	private Customer customer;
	private Destination destination;
	private Attraction attraction;
	private Accommodation accommodation;

	private AccommodationReview accommodationReview;
	private AttractionReview attractionReview;

	private MsgLeave msgLeave;

	public Long cid;

	private List<Booking> bookings= new ArrayList<>();
	private Booking booking;

	private PackageItem item;
	public JsonResult() {
		
	}
	public JsonResult(int state, String message, PackageItem item){
		super();
		this.state = state;
		this.message = message;
		this.item = item;
	}
	public JsonResult(int state, String message, Long id){
		super();
		this.state = state;
		this.message = message;
		this.cid = id;
	}

	public JsonResult(int state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	public JsonResult(int state, String message, Staff staff) {
		super();
		this.state = state;
		this.message = message;
		this.staff = staff;
	}

	public JsonResult(int state, String message,AccommodationReview accommodationReview) {
		super();
		this.state = state;
		this.message = message;
		this.accommodationReview = accommodationReview;
	}

	public JsonResult(int state, String message, AttractionReview attractionReview) {
		super();
		this.state = state;
		this.message = message;
		this.attractionReview = attractionReview;
	}

	public JsonResult(int state, String message, Booking booking) {
		super();
		this.state = state;
		this.message = message;
		this.booking = booking;
	}
	public JsonResult(int state, String message, Customer s) {
		super();
		this.state = state;
		this.message = message;
		this.customer = s;
	}

	public PackageItem getItem() {
		return item;
	}

	public void setItem(PackageItem item) {
		this.item = item;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public JsonResult(int state, String message, MsgLeave msgLeave) {
		this.state = state;
		this.message = message;
		this.msgLeave = msgLeave;
	}

	public JsonResult(int state, String message, List<Booking> bookings) {
		super();
		this.state = state;
		this.message = message;
		this.bookings = bookings;
	}

	public JsonResult(int state, String message, Accommodation accommodation) {
		super();
		this.state = state;
		this.message = message;
		this.accommodation = accommodation;
	}

	public JsonResult(int state, String message, Attraction attraction) {
		super();
		this.state = state;
		this.message = message;
		this.attraction = attraction;
	}
	public JsonResult(int state, String message, Destination destination) {
		super();
		this.state = state;
		this.message = message;
		this.destination = destination;
	}

	public JsonResult(int state, String message, Traffic destination) {
		super();
		this.state = state;
		this.message = message;
		this.traffic = destination;
	}

	public Traffic getTraffic() {
		return traffic;
	}

	public void setTraffic(Traffic traffic) {
		this.traffic = traffic;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Attraction getAttraction() {
		return attraction;
	}

	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + "]";
	}
}
