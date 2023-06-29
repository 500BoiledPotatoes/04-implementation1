package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        System.out.println("chufa");
//        registry.addResourceHandler("../static/**").addResourceLocations("classpath:/static/");
////        System.out.println("file:/"+System.getProperty("user.dir")+"/src/main/resources/static/uploadStorage/");
//        registry.addResourceHandler("../uploadStorage/**").addResourceLocations("file:/"+System.getProperty("user.dir")+"/src/main/resources/static/uploadStorage/");
//    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadStorage\\";
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:upload/");
        System.out.println(path);


    }
}
//public static void main(String[] args) {
//        System.out.println("file:/"+System.getProperty("user.dir"));
//    }