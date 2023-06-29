package com.example.demo.services.PlanningService;

import com.example.demo.model.*;
import com.example.demo.repositories.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;;

@Service
public class BookingService {
    private final AccommodationRepository accommodations;

    private final DestinationRepository destinations;
    private final AttractionRepository attractions;
    private final CustomerRepository customers;
    private final TrafficRepository traffics;

    private final BookingRepository bookings;

    private final PackageItemRepository packageItems;

    private final TravelPackageRepository travelPackages;

    public BookingService(AccommodationRepository accommodations, DestinationRepository destinations, AttractionRepository attractions, CustomerRepository customers, TrafficRepository traffics, BookingRepository bookings, PackageItemRepository packageItems, TravelPackageRepository travelPackages) {
        this.accommodations = accommodations;
        this.destinations = destinations;
        this.attractions = attractions;
        this.customers = customers;
        this.traffics = traffics;
        this.bookings=bookings;
        this.packageItems = packageItems;
        this.travelPackages = travelPackages;
    }

    public Booking addBooking(HttpServletRequest httpServletRequest,Long customerId, Long destinationId, Long attractionId, Long accommodationId, LocalDateTime start, LocalDateTime end) {
        Customer customer = customers.findByCustomerId(customerId);
        Destination destination = destinations.findByDestinationId(destinationId);
        Attraction attraction = null;
        Accommodation accommodation = null;
        if (attractionId != -1) {
            attraction = attractions.findByAttractionID(attractionId);
        }
        if (accommodationId != -1) {
            accommodation = accommodations.findByAccommodationId(accommodationId);
        }
        if (attractionId == -1 && accommodationId == -1) {
            return null;
        }
        if (attractionId != -1 && accommodationId != -1) {
            return null;
        }
        if (start != null && end != null && end.isAfter(start)) {
            Booking booking = new Booking(customer, destination, accommodation, attraction, start, end);
            bookings.save(booking);
            if (accommodation != null) {
                accommodations.save(accommodation);
            }
            if (attraction != null) {
                attractions.save(attraction);
            }
            destinations.save(destination);
            customers.save(customer);
            return booking;
        }
        return null;
    }

    public JsonResult addItem(Long destinationId, Long attractionId, Long accommodationId, LocalDateTime start, LocalDateTime end){
        Destination destination = destinations.findByDestinationId(destinationId);
        if (destination == null){
            return new JsonResult(1,"destination not found");
        }
        Attraction attraction = null;
        Accommodation accommodation = null;
        if (attractionId != -1) {
            attraction = attractions.findByAttractionID(attractionId);
            if (attraction == null) {
                return new JsonResult(1, "attraction not found");
            }
        }
        if (accommodationId != -1) {
            accommodation = accommodations.findByAccommodationId(accommodationId);
            if (accommodation == null){
                return new JsonResult(1, "accommodation not found");
            }
        }
        if (attractionId == -1 && accommodationId == -1) {
            return new JsonResult(1,"accommodation and attraction cannot both be unfilled ", (PackageItem) null);
        }
        if (attractionId != -1 && accommodationId != -1) {
            return new JsonResult(1,"accommodation and attraction cannot both be filled ",(PackageItem) null);
        }
        if (start != null && end != null && end.isAfter(start)) {
            PackageItem item = new PackageItem(destination,accommodation,attraction,start,end);
            packageItems.save(item);
            if (accommodation != null) {
                accommodations.save(accommodation);
            }
            if (attraction != null) {
                attractions.save(attraction);
            }
            destinations.save(destination);
            return new JsonResult(0,"add successfully ",item);

        }else {
            return new JsonResult(1,"please fill the time correctly",(PackageItem) null);
        }

    }

    public TravelPackage createPackage(String name, String nameCh, String price,String path){
        TravelPackage travelPackage = new TravelPackage(name,nameCh,path,price);
        travelPackages.save(travelPackage);
        return travelPackage;
    }
    public TravelPackage addItemToPackage(PackageItem item, TravelPackage travelPackage){
        travelPackage.addItem(item);
        travelPackages.save(travelPackage);
        packageItems.save(item);
        return travelPackage;
    }

    public TravelPackage addItemToPackage(Long itemId, Long travelPackageId){
        TravelPackage travelPackage = travelPackages.findByPackageId(travelPackageId);
        PackageItem item = packageItems.findByPackageItemId(itemId);
        travelPackage.addItem(item);
        travelPackages.save(travelPackage);
        packageItems.save(item);
        return travelPackage;
    }

    public List<Booking> showBooking(Long customerId){
        Customer customer = customers.findByCustomerId(customerId);

        return bookings.findAllByCustomer(customer);
    }

    public List<Booking> showAllBooking(){
        return bookings.findAll();
    }

    public List<Booking> FindBookingByCustomerAndTime(Customer customer, LocalDateTime dateTimeS,LocalDateTime dateTimeE ){
        return bookings.findAllByCustomerAndStartTimeBetween(customer, dateTimeS, dateTimeE);
    }

    public JsonResult editBooking(Long bid ,LocalDateTime start, LocalDateTime end){
        Booking b = bookings.findByBookingId(bid);
        if (start != null && end != null && !start.isBefore(end)){
            return new JsonResult(1, "please input the correct date");
        }
        if (start != null) {

            b.setStartTime(start);
        }
        if (end != null) {
            b.setEndTime(end);
        }
        bookings.save(b);
        return new JsonResult(0, "edit successfully", b);
    }

    public JsonResult editBookingStaff(Long bid ,Long accid, Long attid,  LocalDateTime start, LocalDateTime end){
        Booking b = bookings.findByBookingId(bid);
        if (start != null) {
            b.setStartTime(start);
        }
        if (end != null) {
            b.setEndTime(end);
        }
        if (accid != -1L && attid != -1L){
            return new JsonResult(1, "accommodation and attraction cannot both be filled", b);
        }
        if (accid != -1L){
            Accommodation a = accommodations.findByAccommodationId(accid);
            if (a != null) {
                b.setAccommodation(a);
                b.setAttraction(null);
                b.setDestination(a.getDestination());
            }else {
                return new JsonResult(1, "no such accommodation");
            }
        }
        if (attid != -1L){
            Attraction a = attractions.findByAttractionID(attid);
            if (a != null) {
                b.setAttraction(a);
                b.setAccommodation(null);
                b.setDestination(a.getDestination());
            }else {
                return new JsonResult(1, "no such attraction");
            }
        }
        bookings.save(b);
        return new JsonResult(0, "edit successfully", b);
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }





    public JsonResult deleteBooking(Long bid){
        Booking b = bookings.findByBookingId(bid);
        bookings.delete(b);
        return new JsonResult(0, "delete successfully", b);
    }

    public TravelPackage findPackById(Long pid){
        return travelPackages.findByPackageId(pid);
    }

    public List<TravelPackage> findPackByName(String name){
        return travelPackages.findAllByNameContaining(name);
    }

    public List<PackageItem> findItemByPack(TravelPackage travelPackage){
        return packageItems.findAllByTravelPackage(travelPackage);
    }

    public JsonResult editItem(HttpServletRequest request, Long id, String destination, String accommodation, String attraction, LocalDateTime start, LocalDateTime end){
        PackageItem item = packageItems.findByPackageItemId(id);
        String message = "";
        if (!destination.trim().equals("")){
            Destination d = destinations.findByDestinationName(destination);
            if (d != null){
                item.setDestination(d);
            }
        }
        if (!accommodation.trim().equals("")){
            Accommodation ac = accommodations.findByAccommodationName(accommodation);
            if (ac != null){
                item.setAccommodation(ac);
            }
        }
        if (!attraction.trim().equals("")){
            Attraction at = attractions.findByAttractionName(attraction);
            if (at != null){
                item.setAttraction(at);
            }
        }
        if (!accommodation.trim().equals("") && !attraction.trim().equals("")){
            if (((int)(request.getSession().getAttribute("Language"))) == 1){
                message = "不能同时填写住宿和景点";
            }else {
                message = "accommodation and attraction cannot be both filled";
            }
            return new JsonResult(1, message);
        }
        if (start != null){
            item.setStartDate(start);
        }
        if (end != null){
            item.setEndDate(end);
        }
        if (((int)(request.getSession().getAttribute("Language"))) == 1){
            message = "修改成功";
        }else {
            message = "successfully edited";
        }
        packageItems.save(item);
        return new JsonResult(0,message);
    }

    public JsonResult confirmBooking(Long bid){
        Booking b = bookings.findByBookingId(bid);
        if (b.isConfirmed()){
            return new JsonResult(1, "already confirmed");
        }else {
            b.setConfirmed(true);
            bookings.save(b);
            return new JsonResult(0, "successfully confirmed");
        }

    }
    public JsonResult finishBooking(Long bid){
        Booking b = bookings.findByBookingId(bid);
        if (b.isConfirmed()) {
            if (LocalDateTime.now().isAfter(b.getEndTime())) {
                b.setFinished(true);
                bookings.save(b);
                return new JsonResult(0, "successfully finished");
            }else {
                return new JsonResult(1, "this reservation has not ended yet");
            }
        }else {
            return new JsonResult(1, "this booking is not confirmed, please wait");
        }
    }

    public JsonResult findConfirmedBooking(boolean b){
        List<Booking> bookings1 = bookings.findAllByConfirmed(b);
        return new JsonResult(0, "ok", bookings1);
    }

    public JsonResult findFinishedBooking(boolean b){
        List<Booking> bookings1 = bookings.findAllByFinished(b);
        return new JsonResult(0, "ok", bookings1);
    }

}
