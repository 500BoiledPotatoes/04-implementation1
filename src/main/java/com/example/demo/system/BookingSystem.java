package com.example.demo.system;

import com.example.demo.model.*;
import com.example.demo.services.PlanningService.*;
import com.example.demo.services.UserService.CustomerService;
import com.example.demo.utils.news;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class BookingSystem {
    private final DestinationService destinationService;
    private final AccommodationService accommodationService;
    private final AttractionService attractionService;

    private final CustomerService customerService;

    private final BookingService bookingService;
    private final TrafficService trafficService;

//    private NlpPipeline nlpPipeline;

    @Autowired
    public BookingSystem(DestinationService destinationService, AccommodationService accommodationService, AttractionService attractionService, CustomerService customerService, BookingService bookingService, TrafficService trafficService) {
        this.destinationService = destinationService;
        this.accommodationService = accommodationService;
        this.attractionService = attractionService;
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.trafficService = trafficService;
//        nlpPipeline.init();
    }

    @PostMapping("/addDestination")
    public @ResponseBody JsonResult addDestination(
            HttpServletRequest request,
            @RequestParam(name = "destinationName") String destinationName,
            @RequestParam(name = "destinationIntro") String destinationIntro,
            @RequestParam(name = "destinationNameCn") String destinationNameCn,
            @RequestParam(name = "destinationIntroCn") String destinationIntroCn,
            @RequestParam(name = "file") MultipartFile file,
            Model model
    ) throws IOException {
        String property = System.getProperty("user.dir");
        String path = property + "/upload/";
        String picName = BookingService.getRandomString(6);
        System.out.println(picName);
        String randomSuffix = BookingService.getRandomString(6);
        String filePath = path + randomSuffix + picName;
        String StorePath = "/upload/" + randomSuffix + picName;
        File dest = new File(filePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        JsonResult d = destinationService.addDestination(request,destinationName, destinationNameCn, destinationIntro, destinationIntroCn, StorePath);
        if (d != null) {
            model.addAttribute("destination", d);
            if (d.getState() == 0) {
                return new JsonResult(0, d.getMessage());
            } else {
                return new JsonResult(1, d.getMessage());
            }
        }
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return new JsonResult(1, d.getMessage());
    }

    @PostMapping("/addAttraction")
    public @ResponseBody JsonResult addAttraction(
            HttpServletRequest request,
            @RequestParam(name = "attractionName") String attractionName,
            @RequestParam(name = "attractionIntro") String attractionIntro,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "attractionNameCn") String attractionNameCn,
            @RequestParam(name = "attractionIntroCn") String attractionIntroCn,
            @RequestParam(name = "addressCn") String addressCn,
            @RequestParam(name = "destination") String destination,
            @RequestParam(name = "file") MultipartFile file,
            Model model
    ) throws IOException {
        String property = System.getProperty("user.dir");
//        System.out.println(property);
        String path = property + "/upload/";
        String picName = BookingService.getRandomString(6);
        String randomSuffix = BookingService.getRandomString(6);
        String filePath = path + randomSuffix + picName;
        String StorePath = "/upload/" + randomSuffix + picName;
        File dest = new File(filePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        JsonResult a = attractionService.addAttraction(request,attractionName, attractionNameCn, attractionIntro, attractionIntroCn, address, addressCn, destination,StorePath);
        if (a.getAttraction() != null) {
            model.addAttribute("attraction", a);
            return new JsonResult(0, a.getMessage());
        }
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return new JsonResult(1, a.getMessage());
    }

    @PostMapping("/addAccommodation")
    public @ResponseBody JsonResult addAccommodation(
            HttpServletRequest request,
            @RequestParam(name = "accommodationName") String accommodationName,
            @RequestParam(name = "accommodationIntro") String accommodationIntro,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "accommodationNameCn") String accommodationNameCn,
            @RequestParam(name = "accommodationIntroCn") String accommodationIntroCn,
            @RequestParam(name = "addressCn") String addressCn,
            @RequestParam(name = "destination") String destination,
            @RequestParam(name = "file") MultipartFile file,
            Model model
    ) throws IOException {
        String property = System.getProperty("user.dir");
        String path = property + "/upload/";
        String picName = BookingService.getRandomString(6);
        System.out.println(picName);
        String randomSuffix = BookingService.getRandomString(6);
        String filePath = path + randomSuffix + picName;
        String StorePath = "/upload/" + randomSuffix + picName;
        File dest = new File(filePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        System.out.println(dest.toString());
        file.transferTo(dest);
        JsonResult a = accommodationService.addAccommodation(request,accommodationName, accommodationNameCn, accommodationIntro, accommodationIntroCn, address, addressCn, destination, StorePath);
        if (a.getAccommodation() != null) {
            model.addAttribute("accommodation", a);
            return new JsonResult(0, a.getMessage());
        }
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return new JsonResult(1, a.getMessage());
    }


    @PostMapping("/stafffindDestination")
    public String stafffindDestination(
            HttpServletRequest request,
            @RequestParam(name = "destination") String destination,
            Model model
    ) {
        List<Destination> destinations = destinationService.findDestination(destination);
        for (Destination destination22 : destinations) {
            System.out.println(destination22);
        }
        model.addAttribute("destinations", destinations);
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staffDestinationsList";
    }


    @PostMapping("/findDestination")
    public String findDestination(
            HttpServletRequest request,
            @RequestParam(name = "destination") String destination,
            Model model
    ) {
        List<Destination> destinations = destinationService.findDestination(destination);
        for (Destination destination22 : destinations) {
            System.out.println(destination22);
        }
        model.addAttribute("results", destinations);
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "DestinationsList";
    }

    @PostMapping("/addBooking")
    public String addBooking(@RequestParam(name = "destinationId") Long destinationId,
                             @RequestParam(name = "accommodationId") Long accommodationId,
                             @RequestParam(name = "attractionId") Long attractionId,
                             @RequestParam(name = "customerId") Long customerId,

                             @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
                             @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
                             @RequestParam(name = "startTime") LocalDateTime start,

                             @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
                             @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
                             @RequestParam(name = "endTime") LocalDateTime end,
                             HttpServletRequest request,
                             Model model) {
        HttpSession session = request.getSession();
        Booking booking = bookingService.addBooking(request,customerId, destinationId, attractionId, accommodationId, start, end);
        model.addAttribute("booking", booking);
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("customerBookings", bookingService.showBooking(((Customer)(session.getAttribute("customer"))).getCustomerId()));
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "client";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }


    @GetMapping("/Destinations")
    public String toDestinations(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<Destination> destinations = destinationService.findDestination("");
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("destinations", destinations);
        return "DestinationsList";
    }


    @GetMapping("/staffDestinations")
    public String tostaffDestinations(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<Destination> destinations = destinationService.findDestination("");
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("staff", session.getAttribute("staff"));
        model.addAttribute("destinations", destinations);
        return "StaffDestinationsList";
    }


    @GetMapping("/DestinationDetails")
    public String toDestinationDetails(@RequestParam(name = "destinationId") Long destinationId,
                                       HttpServletRequest request,
                                       Model model) {
        HttpSession session = request.getSession();
        Destination destination = destinationService.findDestinationById(destinationId);
        List<Accommodation> accommodations = accommodationService.findAccommodationByDestination(destination);
        model.addAttribute("accommodations", accommodations);
        List<Attraction> attractions = attractionService.findAttractionsByDestination(destination);
        model.addAttribute("attractions", attractions);
        model.addAttribute("destination", destination);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "DestinationDetails";
    }


    @GetMapping("/staffDestinationDetails")
    public String tostaffDestinationDetails(@RequestParam(name = "destinationId") Long destinationId,
                                            HttpServletRequest request,
                                            Model model) {
        HttpSession session = request.getSession();
        Destination destination = destinationService.findDestinationById(destinationId);
        List<Accommodation> accommodations = accommodationService.findAccommodationByDestination(destination);
        model.addAttribute("accommodations", accommodations);
        List<Attraction> attractions = attractionService.findAttractionsByDestination(destination);
        model.addAttribute("attractions", attractions);
        model.addAttribute("destination", destination);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffDestinationDetails";
    }


    @GetMapping("/staffAccommodations")
    public String tostaffAccommodations(
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession();
        List<Accommodation> accommodations = accommodationService.findAccommodation("");
        model.addAttribute("accommodations", accommodations);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staffAccommodationsList";
    }


    @GetMapping("/Accommodations")
    public String toAccommodations(
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession();
        List<Accommodation> accommodations = accommodationService.findAccommodation("");
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("accommodations", accommodations);
        return "AccommodationsList";
    }


    @GetMapping("/staffAttractions")
    public String tostaffAttractions(
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession();
        List<Attraction> attractions = attractionService.findAttraction("");
        model.addAttribute("attractions", attractions);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staffAttractionsList";
    }


    @GetMapping("/Attractions")
    public String toAttractions(
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession();
        List<Attraction> attractions = attractionService.findAttraction("");
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("attractions", attractions);
        return "AttractionsList";
    }


    @GetMapping("/attractionDetail")
    public String attractionDetail(@RequestParam(name = "attractionId") Long attractionId,
                                   HttpServletRequest request,
                                   Model model) {
        HttpSession session = request.getSession();
        Attraction attraction = attractionService.findAttractionById(attractionId);
        model.addAttribute("score",attraction.getScore());
        model.addAttribute("attractionDetail", attraction);
        model.addAttribute("attractionReviews", attraction.getAttractionReviews());
        session.setAttribute("attractionReviews", attraction.getAttractionReviews());
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "AttractionDetails";
    }


    @GetMapping("/accommodationDetail")

    public String accommodationDetail(@RequestParam(name = "accommodationId") Long accommodationId,
                                      HttpServletRequest request,
                                      Model model) {
        HttpSession session = request.getSession();
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);
        List<AccommodationReview> accommodationReviews = accommodationService.findReviewByAcc(accommodationId);
        model.addAttribute("accommodationReviews", accommodationReviews);
        model.addAttribute("accommodationDetail", accommodation);
        model.addAttribute("score",accommodation.getScore());
        session.setAttribute("accommodationId",accommodationId);
        session.setAttribute("accommodationReviews", accommodationReviews);
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "AccommodationDetails";
    }

    @PostMapping("/addItem")
    public @ResponseBody JsonResult addItem(
            @RequestParam(name = "destinationId") Long destinationId,
            @RequestParam(name = "packageId") Long packageId,
            @RequestParam(name = "accommodationId") Long accommodationId,
            @RequestParam(name = "attractionId") Long attractionId,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "startTime") LocalDateTime start,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "endTime") LocalDateTime end,
            Model model
    ) {
        JsonResult item = bookingService.addItem(destinationId, attractionId, accommodationId, start, end);
        if (item.getItem() !=  null) {
            TravelPackage travelPackage = bookingService.findPackById(packageId);
            TravelPackage newTravelPackage = bookingService.addItemToPackage(item.getItem(), travelPackage);
            model.addAttribute("item", item);
            return new JsonResult(item.getState(),item.getMessage());
        }else {
            return new JsonResult(item.getState(),item.getMessage());
        }
    }

    @GetMapping("/toAddPackage")
    public String toAddPackage(HttpServletRequest request,
                               Model model) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffAddPac";
    }


    @PostMapping("/createPackage")
    public @ResponseBody JsonResult createPackage(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "nameCn") String nameCh,
            @RequestParam(name = "price") String price,
            @RequestParam(name = "file") MultipartFile file,
            Model model
    ) throws IOException {
        String property = System.getProperty("user.dir");
        String path = property + "/upload/";
        String picName = BookingService.getRandomString(6);
        System.out.println(picName);
        String randomSuffix = BookingService.getRandomString(6);
        String filePath = path + randomSuffix + picName;
        String StorePath = "/upload/" + randomSuffix + picName;
        File dest = new File(filePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        TravelPackage travelPackage = bookingService.createPackage(name, nameCh, price,StorePath);
        model.addAttribute("travelPackage", travelPackage);
        return new JsonResult(0, "Add successfully");
    }

    @PostMapping("/addItemToPackage")
    public String addItemToPackage(
            @RequestParam(name = "itemId") Long itemId,
            @RequestParam(name = "travelPackageId") Long travelPackageId,
            Model model

    ) {
        TravelPackage travelPackage = bookingService.addItemToPackage(itemId, travelPackageId);
        model.addAttribute("travelPackage", travelPackage);
        return "index";
    }

    @GetMapping("/showBookingsOfCustomer")
    public String getBooking(
            @RequestParam(name = "customerId") Long customerId,
            HttpServletRequest request,
            Model model

    ) {
        HttpSession session = request.getSession();
        List<Booking> bookings = bookingService.showBooking(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("bookings", bookings);

        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "CustomerBookingList";
    }

    @GetMapping("/showAllBookings")
    public String getBooking(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Booking> bookings = bookingService.showAllBooking();
        session.setAttribute("bookings", bookings);
        model.addAttribute("bookings", bookings);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "BookingList";
    }

    @GetMapping("/page/{page}")
    public String PagePresentation(Model model, @PathVariable("page") int page) {
        int pageSize = 9;
        Page<Destination> destinations = destinationService.findPageDestination("", PageRequest.of(page - 1, pageSize));
        model.addAttribute("destinations", destinations);
        int totalPages = destinations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "temp";
    }

    @GetMapping("/pageIndex")
    public String pageIndex(Model model) {
        return PagePresentation(model, 1);
    }


    @PostMapping("/stafffindAccommodation")
    public String stafffindAccommodation(
            @RequestParam(name = "accommodation") String accommodationn,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Accommodation> accommodation = accommodationService.findAccommodation(accommodationn);
        model.addAttribute("accommodations", accommodation);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staffAccommodationsList";
    }


    @PostMapping("/findAccommodation")
    public String findAccommodation(
            @RequestParam(name = "accommodation") String accommodationn,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Accommodation> accommodation = accommodationService.findAccommodation(accommodationn);
        model.addAttribute("accommodations", accommodation);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "AccommodationsList";
    }


    @PostMapping("/stafffindAttraction")
    public String stafffindAttraction(
            @RequestParam(name = "attraction") String attraction,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Attraction> attraction1 = attractionService.findAttraction(attraction);
        model.addAttribute("attractions", attraction1);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staffAttractionsList";
    }

    @PostMapping("/findAttraction")
    public String findAttraction(
            @RequestParam(name = "attraction") String attraction,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Attraction> attraction1 = attractionService.findAttraction(attraction);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("attractions", attraction1);
        return "AttractionsList";
    }

    @GetMapping("/destinations/FirstPage")
    public String viewAllDestinations(HttpServletRequest request, Model model) {
        return viewPage(model, request, 1, 9);
    }

//    @GetMapping("/destinations/FirstPage")
//    public String viewFirstPage(Model model){
//        return viewMainPage(model,1,Optional.of(""));
//    }

//    @GetMapping("/destinations/page/{pageNumber}")
//    public String viewMainPage(Model model,
//                           @PathVariable("pageNumber") int currentPage,
//                           @RequestParam("keyword") Optional<String> keyword
//    ) {
//        String currentKeyword = "";
//        if (!keyword.isPresent()) {
//            currentKeyword = keyword.toString();
//        }
//        int pageSize = 2;
//        Page<Destination> page;
//        if (currentKeyword.isEmpty()) {
//            page = destinationService.findDesPaginated(currentPage, pageSize);
//        } else {
//            page = destinationService.findDesPaginatedByWord(currentKeyword, currentPage, pageSize);
//        }
//        List<Destination> destinations = page.getContent();
//        model.addAttribute("destinations", destinations);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("keyword", currentKeyword);
//
//        List<Integer> pageNumbers = IntStream.rangeClosed(1, page.getTotalPages())
//                .boxed()
//                .collect(Collectors.toList());
//        model.addAttribute("pageNumbers", pageNumbers);
//        return "DestinationsList";
//    }

    @GetMapping("/destinations/pageNum")
    public String viewPage(Model model,
                           HttpServletRequest request,
                           @RequestParam(value = "page", defaultValue = "1") int pageNum,
                           @RequestParam(value = "size", defaultValue = "9") int pageSize) {
        HttpSession session = request.getSession();
        Page<Destination> page = destinationService.findDesPaginatedByWord("", pageNum, pageSize);
        List<Destination> results = page.getContent();
        model.addAttribute("results", results);
        System.out.println(pageNum);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("query", "");
        List<Integer> pageNumbers = IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "DestinationsList";
    }

    @GetMapping("/search")
    public String search(Model model,
                         HttpServletRequest request,
                         @RequestParam("name") String query,
                         @RequestParam(value = "page", defaultValue = "1") int pageNum,
                         @RequestParam(value = "size", defaultValue = "9") int pageSize) {
        HttpSession session = request.getSession();
        Page<Destination> page = destinationService.findDesPaginatedByWord(query, pageNum, pageSize);
        List<Destination> results = page.getContent();
        model.addAttribute("results", results);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("query", query);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("language", session.getAttribute("Language"));
        return "temp2";
    }

    @PostMapping("/addAttractionReview")
    public @ResponseBody JsonResult addAttractionReview(
            Model model,
            @RequestParam(name = "customerId") Long cid,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "attractionId") Long aid
    ) {
        AttractionReview attractionReview = attractionService.addAttReview(cid, content, aid);
        System.out.println(attractionReview.toString());
//        int score=nlpPipeline.estimatingSentiment(content);
        model.addAttribute("attractionReview", attractionReview);
        return new JsonResult(0, "Post successfully");
    }

    @PostMapping("/addAccommodationReview")
    public @ResponseBody JsonResult addAccommodationReview(
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "customerId") Long cid,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "accommodationId") Long aid
    ) {
        JsonResult accommodationReview = accommodationService.addAccReview(request,cid, content, aid);
        if (accommodationReview != null) {
            model.addAttribute("accommodationReview", accommodationReview);
            if (accommodationReview.getState() == 0) {
                return new JsonResult(0, accommodationReview.getMessage());
            } else {
                return new JsonResult(1, accommodationReview.getMessage());
            }
        }
        return new JsonResult(1, accommodationReview.getMessage());
    }

    @PostMapping("/findAccReview")
    public String findAccReview(
            Model model,
            @RequestParam(name = "accommodationId") Long aid
    ) {
        List<AccommodationReview> accommodationReviews = accommodationService.findReviewByAcc(aid);
        model.addAttribute("accommodationReviews", accommodationReviews);
        return "index";
    }

    @PostMapping("/findAttReview")
    public String findAttReview(
            Model model,
            @RequestParam(name = "attractionId") Long aid
    ) {
        List<AttractionReview> attractionReviews = attractionService.findReviewByAtt(aid);
        model.addAttribute("attractionReviews", attractionReviews);
        return "index";
    }

    @PostMapping("/findBookingOfCustomerByDate")
    public @ResponseBody JsonResult findBookingOfCustomerByDate(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "day") String day,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "year") int year

    ) {
        LocalDateTime dateTimeS = LocalDateTime.of(year, Month.of(month), Integer.parseInt(day), 0, 0, 0);
        LocalDateTime dateTimeE = LocalDateTime.of(year, Month.of(month), Integer.parseInt(day), 23, 59, 59);
        Customer c = customerService.findCustomerById(((Customer)(request.getSession().getAttribute("customer"))).getCustomerId());
        // 改动态
        List<Booking> bookings = bookingService.FindBookingByCustomerAndTime(c, dateTimeS, dateTimeE);
        model.addAttribute("bookings", bookings);
        if (bookings.size() > 0) {
            System.out.println(year + "-" + month + "-" + day + ":");
            System.out.println(bookings.get(0).getBookingId());
            return new JsonResult(0, "ok");
        }

//        } else {
//            return new JsonResult(1, "no booking found");
//        }
        return new JsonResult(1, "no booking found");
    }

    @GetMapping("/findBookingOfCustomerByDate2")
        public String findBookingOfCustomerByDate2(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "day") String day,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "year") int year

    ) {
        LocalDateTime dateTimeS = LocalDateTime.of(year, Month.of(month), Integer.parseInt(day), 0, 0, 0);
        LocalDateTime dateTimeE = LocalDateTime.of(year, Month.of(month), Integer.parseInt(day), 23, 59, 59);
        Customer c = customerService.findCustomerById(((Customer)(request.getSession().getAttribute("customer"))).getCustomerId());
        // 改动态
        List<Booking> bookings = bookingService.FindBookingByCustomerAndTime(c, dateTimeS, dateTimeE);
        HttpSession session = request.getSession();
        session.setAttribute("bookings", bookings);
        model.addAttribute("bookings", bookings);
        model.addAttribute("language", session.getAttribute("Language"));
        //   System.out.println(bookings.size());
        if (bookings.size() > 0) {
            return "client::list";
//            return new JsonResult(0, "OKKKkk");
        } else {
            return "client::list";
//            return new JsonResult(1, "no booking found");
        }
    }

    @GetMapping("/calenderBooking")
    public String calenderBooking(){
        return "CalenderBooking";
    }

    @GetMapping("/indexCalender")
    public String indexCAlender() {
        return "indexCalender";
    }

    @PostMapping("/editBooking")
    public @ResponseBody JsonResult editBooking(
            Model model,
            @RequestParam(name = "bid") Long bid,

            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "start") LocalDateTime start,

            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "end") LocalDateTime end

    ) {
        JsonResult jr = bookingService.editBooking(bid, start, end);
        return new JsonResult(jr.getState(), jr.getMessage());
    }

    @PostMapping("/editBookingStaff")
    public @ResponseBody JsonResult editBookingStaff(
            Model model,
            @RequestParam(name = "bid") Long bid,
            @RequestParam(name = "accid") Long accid,
            @RequestParam(name = "attid") Long attid,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "start") LocalDateTime start,

            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            @RequestParam(name = "end") LocalDateTime end

    ) {
        JsonResult jr = bookingService.editBookingStaff(bid, accid, attid,start, end);
        return new JsonResult(jr.getState(), jr.getMessage());
    }


    @PostMapping("/deleteBooking")
    public @ResponseBody JsonResult deleteBooking(
            Model model,
            @RequestParam(name = "bid") Long bid
    ) {
        JsonResult jr = bookingService.deleteBooking(bid);
        return new JsonResult(0, jr.getMessage());
    }
    @PostMapping("/deleteAccommodation")
    public @ResponseBody JsonResult deleteAccommodation(
            Model model,
            @RequestParam(name = "bid") Long aid
    ) {
        JsonResult jr = accommodationService.deleteAccommodation(aid);
        return new JsonResult(0, jr.getMessage());
    }

    @PostMapping("/deleteAttraction")
    public @ResponseBody JsonResult deleteAttraction(
            Model model,
            @RequestParam(name = "bid") Long bid
    ) {
        JsonResult jr = attractionService.deleteAttraction(bid);
        return new JsonResult(0, jr.getMessage());
    }




    @PostMapping("/popularPlaces")
    public String popularPlacesGnerating(Model model) {
        news n = new news();
        List<popularPlace> result = n.generateList();
        model.addAttribute("popular places", result);
        return "NewsRank";
    }


    @GetMapping("/staffAddTra")
    public String StaffAddTra(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffAddTra";
    }

    @PostMapping("/addTraffic")
    public @ResponseBody JsonResult addTraffic(
            Model model,
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "typeCn") String typeCn,
            @RequestParam(name = "from") String from,
            @RequestParam(name = "fromCn") String fromCn,
            @RequestParam(name = "to") String to,
            @RequestParam(name = "toCn") String toCn,
            @RequestParam(name = "price") String price,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            @RequestParam(name = "start")
            LocalDateTime startTime,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            @RequestParam(name = "end")
            LocalDateTime endTime
    ) {
        JsonResult jsonResult = trafficService.addTraffic(httpServletRequest, type, typeCn, from, fromCn, to, toCn, startTime, endTime, price);
        return new JsonResult(jsonResult.getState(), jsonResult.getMessage(), jsonResult.getTraffic());
    }

    @PostMapping("/addTrafficBooking")
    public @ResponseBody JsonResult addTrafficBooking(
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "traffic_id") Long tid
    ) {
        JsonResult jsonResult = trafficService.addBooking(httpServletRequest, ((Customer)(httpServletRequest.getSession().getAttribute("customer"))).getCustomerId(), tid);
        return new JsonResult(jsonResult.getState(), jsonResult.getMessage());
    }



    @GetMapping("/trafficList")
    public String trafficList(
            HttpServletRequest request,
            Model model
    ){
        HttpSession session = request.getSession();
        List<Traffic> traffics = trafficService.findByType("");
        model.addAttribute("traffics", traffics);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "TrafficList";
    }

    @GetMapping("/staffTrafficList")
    public String searchTraffic(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<Traffic> traffics = trafficService.findByType("");
        model.addAttribute("traffics", traffics);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffTrafficList";
    }

    @PostMapping("/getTrafficByCustomer")
    public String getTrafficByCustomer(
            HttpServletRequest httpServletRequest,
            Model model,
            @RequestParam(name = "customer_id") Long cid
    ) {
        Customer customer = customerService.findCustomerById(cid);
        List<Traffic> traffics = customer.getTraffics();
        model.addAttribute("traffics", traffics);
        HttpSession session = httpServletRequest.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "index";
    }

    @GetMapping("/changePackToBooking")
    public String changePackToBooking(
            HttpServletRequest httpServletRequest,
            Model model,
            @RequestParam(name = "pack_id") Long pid
    ) {
        TravelPackage travelPackage = bookingService.findPackById(pid);
        Customer c =(Customer) httpServletRequest.getSession().getAttribute("customer");
        for (PackageItem item : travelPackage.getItems()) {
            Long accid = 0L;
            Long attid = 0L;
            if (item.getAccommodation() == null) {
                accid = -1L;
            } else {
                accid = item.getAccommodation().getAccommodationId();
            }
            if (item.getAttraction() == null) {
                attid = -1L;
            } else {
                attid = item.getAttraction().getAttractionID();
            }
            bookingService.addBooking(httpServletRequest,c.getCustomerId(), item.getDestination().getDestinationId(), attid, accid, item.getStartDate(), item.getEndDate());
        }
        return "redirect:/toClient";
    }

    @GetMapping("/toRank")
    public String toRank(
            HttpServletRequest httpServletRequest,
            Model model
    ) {
        List<Traffic> traffics = trafficService.findByType("");
        model.addAttribute("traffics", traffics);
        HttpSession session = httpServletRequest.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffTrafficList";
    }

    @GetMapping("/getPacks")
    public String findPacks(
            HttpServletRequest httpServletRequest,
            Model model
    ){
        List<TravelPackage> packages = bookingService.findPackByName("");
        model.addAttribute("packs", packages);
        HttpSession session = httpServletRequest.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "PackageList";
    }

    @GetMapping("/getItems")
    public String findItems(
            HttpServletRequest httpServletRequest,
            Model model,
            @RequestParam(name = "packId") Long pid
    ){
        TravelPackage pack = bookingService.findPackById(pid);
        List<PackageItem> items = pack.getItems();
        model.addAttribute("items", items);
        model.addAttribute("pack", pack);
        model.addAttribute("packId", pack.getPackageId());
        HttpSession session = httpServletRequest.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "PackageItemsList";
    }
    @GetMapping("/getCustomerTrafficBooking")
    public String findTrafficBooking(
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();

        Customer c = customerService.findCustomerById(((Customer)session.getAttribute("customer")).getCustomerId());
        List<Traffic> traffics = c.getTraffics();
//        System.out.println(traffics.get(0).getTrafficId());
        model.addAttribute("traffics", traffics);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "CusTrafficBooking";
    }

    @PostMapping("/editAttraction")
    public @ResponseBody JsonResult editAttraction(
            HttpServletRequest httpServletRequest,
            @RequestParam(name = "aid") Long aid ,
            @RequestParam(name = "attractionName") String attractionName,
            @RequestParam(name = "attractionNameCn") String attractionNameCn,
            @RequestParam(name = "attractionIntro") String attractionIntro,
            @RequestParam(name = "attractionIntroCn") String attractionIntroCn,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "addressCn") String addressCn,
            Model model
    ){
//        Customer c =(Customer) httpServletRequest.getSession().getAttribute("customer");
        JsonResult jsonResult = attractionService.editAtt(httpServletRequest, aid, attractionName, attractionNameCn, attractionIntro, attractionIntroCn, address, addressCn);
        HttpSession session = httpServletRequest.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return jsonResult;
    }




    @GetMapping("/AttReviewCommon")
    public String AttReviewCommon(
            @RequestParam(name = "attractionId") Long attractionId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Attraction attraction = attractionService.findAttractionById(attractionId);
        List<AttractionReview> ReviewList=attraction.getAttractionReviews();


        for(AttractionReview attractionReview : ReviewList){
            System.out.println(attractionReview.getScore());
        }
        session.setAttribute("attractionReviews",ReviewList);
        return "Comment::list";
    }



    @GetMapping("/AccReviewCommon")
    public String AccReviewCommon(
            @RequestParam(name = "accommodationId") Long accommodationId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);
        List<AccommodationReview> ReviewList=accommodation.getAccommodationReviews();


        for(AccommodationReview accommodationReview : ReviewList){
            System.out.println(accommodationReview.getScore());
        }
        session.setAttribute("accommodationReviews",ReviewList);
        return "Comment::list2";
    }


    @GetMapping("/AccReviewGood")
    public String AccReviewGood(
            @RequestParam(name = "accommodationId") Long accommodationId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);
        List<AccommodationReview> ReviewList=accommodation.getAccommodationReviews();

        ReviewList.removeIf(accommodationReview -> accommodationReview.getScore() < 3);

        for(AccommodationReview accommodationReview : ReviewList){
            System.out.println(accommodationReview.getScore());
        }
        session.setAttribute("accommodationReviews",ReviewList);
        return "Comment::list2";
    }


    @GetMapping("/AccReviewBad")
    public String AccReviewBad(
            @RequestParam(name = "accommodationId") Long accommodationId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);
        List<AccommodationReview> ReviewList=accommodation.getAccommodationReviews();

        ReviewList.removeIf(accommodationReview -> accommodationReview.getScore() > 2);

        for(AccommodationReview accommodationReview : ReviewList){
            System.out.println(accommodationReview.getScore());
        }
        session.setAttribute("accommodationReviews",ReviewList);
        return "Comment::list2";
    }

    @GetMapping("/AttReviewGood")
    public String AttReviewGood(
            @RequestParam(name = "attractionId") Long attractionId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Attraction attraction = attractionService.findAttractionById(attractionId);
        List<AttractionReview> ReviewList=attraction.getAttractionReviews();

        ReviewList.removeIf(attractionReview -> attractionReview.getScore() < 3);

        for(AttractionReview attractionReview : ReviewList){
            System.out.println(attractionReview.getScore());
        }
        session.setAttribute("attractionReviews",ReviewList);

        return "Comment::list";
    }

    @GetMapping("/AttReviewBad")
    public String AttReviewBad(
            @RequestParam(name = "attractionId") Long attractionId,
            HttpServletRequest httpServletRequest,
            Model model
    ){
        HttpSession session = httpServletRequest.getSession();
        Attraction attraction = attractionService.findAttractionById(attractionId);
        List<AttractionReview> ReviewList=attraction.getAttractionReviews();

        ReviewList.removeIf(attractionReview -> attractionReview.getScore() > 2);

        for(AttractionReview attractionReview : ReviewList){
            System.out.println(attractionReview.getScore());
        }
        session.setAttribute("attractionReviews",ReviewList);

        return "Comment::list";
    }


    @PostMapping("/editDestination")
    public @ResponseBody JsonResult editDes(
            @RequestParam(name = "did") Long did,
            @RequestParam(name = "destinationName") String destinationName,
            @RequestParam(name = "destinationNameCn") String destinationNameCn,
            @RequestParam(name = "destinationIntro") String  destinationIntro,
            @RequestParam(name = "destinationIntroCn") String destinationIntroCn,
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        JsonResult jsonResult = destinationService.editDestination(request,did,destinationName,destinationNameCn,destinationIntro,destinationIntroCn);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return new JsonResult(jsonResult.getState(), jsonResult.getMessage());

    }

    @PostMapping("/editAccommodation")
    public @ResponseBody JsonResult editAcc(
            @RequestParam(name = "aid") Long aid,
            @RequestParam(name = "accommodationName") String accommodationName,
            @RequestParam(name = "accommodationNameCn") String accommodationNameCn,
            @RequestParam(name = "accommodationIntro") String  accommodationIntro,
            @RequestParam(name = "accommodationIntroCn") String accommodationIntroCn,
            @RequestParam(name = "accommodationAddress") String  accommodationAddress,
            @RequestParam(name = "accommodationAddressCn") String accommodationAddressCn,
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        JsonResult jsonResult = accommodationService.editAcc(request,aid,accommodationName,accommodationNameCn,accommodationIntro,accommodationIntroCn,accommodationAddress,accommodationAddressCn);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return new JsonResult(jsonResult.getState(), jsonResult.getMessage());
    }


    @PostMapping("/confirmBooking")
    public @ResponseBody JsonResult confirmBooking(
            @RequestParam(name = "bid") Long bid,
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return bookingService.confirmBooking(bid);
    }


    @PostMapping("/finishBooking")
    public @ResponseBody JsonResult finishBooking(
            @RequestParam(name = "bid") Long bid,
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return bookingService.finishBooking(bid);
    }

    @PostMapping("/findAllBooking")
    public String findAllBooking(
            Model model,
            HttpServletRequest request
    ){
        List<Booking> bookings = bookingService.showAllBooking();
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        session.setAttribute("bookings", bookings);
        model.addAttribute("bookings", bookings);
        return "BookingList::list";
    }



    @PostMapping("/findUnConfirmBooking")
    public String findUnConfirmBooking(
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        JsonResult jsonResult = bookingService.findConfirmedBooking(false);
        session.setAttribute("bookings", jsonResult.getBookings());
        return "BookingList::list";
    }


    @PostMapping("/findUnFinishedBooking")
    public String  findUnfinishedBooking(
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        JsonResult jsonResult = bookingService.findFinishedBooking(false);
        session.setAttribute("bookings", jsonResult.getBookings());
        return "BookingList::list";
    }


    @GetMapping("/threeElite2")
    public String  threeElite2(
            Model model,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        Long idNum=(Long) session.getAttribute("accommodationId");
        System.out.println(idNum);
        int result= (int) (idNum%12);
        String result1="three"+result;
        return result1;
    }

    @PostMapping("/DeleteTrafficBooking")
    public @ResponseBody JsonResult deleteTrafficBooking(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "tid") Long tid
    ){
        HttpSession session = request.getSession();
        System.out.println(request.getSession());
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        Customer c = (Customer) session.getAttribute("customer");
        JsonResult js = trafficService.deleteBooking(c.getCustomerId(),tid);
        return new JsonResult(js.getState(), js.getMessage());
    }



}
