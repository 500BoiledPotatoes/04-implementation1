package com.example.demo.config;

import com.example.demo.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



public class InterceptorConfig implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor())
                    .addPathPatterns("/**") //
                    .excludePathPatterns(
                            "/Destinations",
                            "/DeleteTrafficBooking",
                            "/threeElite2",
                            "/finishBooking",
                            "/AttReviewGood",
                            "/AttReviewBad",
                            "/AccReviewCommon",
                            "/AccReviewGood",
                            "/AccReviewBad",
                            "/AttReviewCommon",
                            "/getCustomerTrafficBooking",
                            "/getItems",
                            "/getPacks",
                            "/changePackToBooking",
                            "/getTrafficByCustomer",
                            "/trafficList",
                            "/addTrafficBooking",
                            "/popularPlaces",
                            "/editBooking",
                            "/deleteBooking",
                            "/findBookingOfCustomerByDate",
                            "/findBookingOfCustomerByDate2",
                            "/addAccommodationReview",
                            "/addAttractionReview",
                            "/findAccommodation",
                            "/findAttraction",
                            "/Attractions",
                            "/attractionDetail",
                            "/accommodationDetail",
                            "/Accommodations",
                            "/DestinationDetails",
                            "/addBooking",
                            "/findDestination",
                            "/",
                            "/login",
                            "/slogin",
                            "/toLogin",
                            "/changeLanguage",
                            "/toLogin",
                            "/tosLogin",
                            "/toContact",
                            "/toReadMore",
                            "/page/{page}",
                            "/pageIndex",
                            "/destinations/FirstPage",
                            "/destinations/pageNum",
                            "/findAccReview",
                            "/findAttReview"
                    ); //排除该登录地址或添加其他
        }

}
