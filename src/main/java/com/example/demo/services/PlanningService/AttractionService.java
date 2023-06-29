package com.example.demo.services.PlanningService;

import com.example.demo.model.*;
import com.example.demo.repositories.AttractionRepository;
import com.example.demo.repositories.AttractionReviewRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.DestinationRepository;
import com.example.demo.semanticTraining.NlpPipeline;
import com.example.demo.system.UserSystem;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;

@Service
public class AttractionService {
    private final AttractionRepository attractions;
    private final CustomerRepository customers;

    private final AttractionReviewRepository attractionReviews;
    private final DestinationRepository destinations;

    private NlpPipeline nlpPipeline;

    public AttractionService(AttractionRepository a, CustomerRepository customers, AttractionReviewRepository attractionReviews, DestinationRepository destinations) {
        this.attractions = a;
        this.customers = customers;
        this.attractionReviews = attractionReviews;
        this.destinations = destinations;
        nlpPipeline=new NlpPipeline();
        nlpPipeline.init();
    }
    public JsonResult editAtt(HttpServletRequest httpServletRequest, Long aid , String attractionName, String attractionNameCn, String attractionIntro, String attractionIntroCn, String address, String addressCn){
        Attraction b = attractions.findByAttractionID(aid);
        String message = "";
        if (!Objects.equals(attractionName, b.getAttractionName()) && !attractionName.trim().equals("")) {
            if (attractions.findByAttractionName(attractionName) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Attraction Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Attraction Already Exist";
                }else {
                    message = "该景点已经存在";
                }
                return new JsonResult(1, message, (Attraction) null);
            } else {
                b.setAttractionName(attractionName);
            }
        }
        if (!Objects.equals(attractionNameCn, b.getAttractionNameCn()) && !attractionNameCn.trim().equals("")) {
            if (attractions.findByAttractionNameCn(attractionNameCn) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Attraction Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Attraction Already Exist";
                }else {
                    message = "该景点已经存在";
                }
                return new JsonResult(1, message, (Attraction) null);
            } else {
                b.setAttractionNameCn(attractionNameCn);
            }
        }
        if (!Objects.equals(attractionIntro, b.getAttractionIntro()) && !Objects.equals(attractionIntro, "")) {
            b.setAttractionIntro(attractionIntro);
        }
        if (!Objects.equals(attractionIntroCn, b.getAttractionIntroCn()) && !Objects.equals(attractionIntroCn, "")) {
            b.setAttractionIntroCn(attractionIntroCn);
        }
        if (!Objects.equals(address, b.getAddress()) && !Objects.equals(address, "")) {
            b.setAddress(address);
        }
        if (!Objects.equals(addressCn, b.getAddressCn()) && !Objects.equals(addressCn, "")) {
            b.setAddressCn(addressCn);
        }
        attractions.save(b);
        return new JsonResult(0, "edit successfully");
    }
    public JsonResult addAttraction(HttpServletRequest httpServletRequest, String attractionName, String attractionNameCn, String attractionIntro, String attractionIntroCn, String address, String addressCn, String destination,String storePath) {
        String message = "";
        if (!attractionName.trim().equals("") && !attractionIntro.trim().equals("")&&!attractionNameCn.trim().equals("") && !attractionIntroCn.trim().equals("")) {
            if (attractions.findByAttractionName(attractionName) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Attraction Already Exist";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Attraction Already Exist";
                }else {
                    message = "该景点已经存在";
                }
                return new JsonResult(1,message,(Attraction) null);
            } else {
                Destination de = destinations.findByDestinationName(destination);
                if (de != null) {
                    Attraction d = new Attraction(attractionName,attractionNameCn, attractionIntro,attractionIntroCn, address,addressCn, de,storePath);
                    attractions.save(d);
                    de.addAttractions(d);
                    destinations.save(de);
                    if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                        message = "Attraction Add Successfully";
                    }
                    else
                    if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                        message = "Attraction Add Successfully";
                    }else {
                        message = "该景点成功添加";
                    }
                    return new JsonResult(0,message,d);
                } else {
                    if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                        message = "Destination doesn't already existed";
                    }
                    else
                    if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                        message = "Destination doesn't already existed";
                    }else {
                        message = "该地点不存在";
                    }
                    return new JsonResult(1,message,(Attraction) null);
                }
            }
        } else {
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "Please Fill All the Input Fields";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "Please Fill All the Input Fields";
            }else {
                message = "请填写全部内容";
            }
            return new JsonResult(1, message, (Attraction) null);
        }
    }
    public JsonResult deleteAttraction(Long aid){
        Attraction attraction=attractions.findByAttractionID(aid);
        attractions.delete(attraction);
        return new JsonResult(0,"delete successfully",attraction);
    }


    public AttractionReview addAttReview(Long cid, String content, Long attid) {
        Customer customer = customers.findByCustomerId(cid);
        Attraction attraction = attractions.findByAttractionID(attid);
        int score = nlpPipeline.estimatingSentiment(content);
        System.out.println(score);
        LocalDateTime localDateTime = LocalDateTime.now(); // 获取当前时间
        int year = localDateTime.getYear(); // 获取年份
        int month = localDateTime.getMonthValue(); // 获取月份
        int day = localDateTime.getDayOfMonth(); // 获取月中的天数
        int hour = localDateTime.getHour(); // 获取当前的小时
        int minute = localDateTime.getMinute(); // 获取当前分钟
        int second = localDateTime.getSecond(); // 获取当前秒数
        LocalDateTime time = LocalDateTime.of(year, Month.of( month ), day ,hour,minute,second);
        AttractionReview ar = new AttractionReview(time, score, customer, content, attraction);
        attraction.addReview(ar);
        int attractionScore=attraction.getScore();
        int tempSum=attraction.getTempSum();
//        if(score==1|score==0){
//            tempSum+=score-4;
//        }
//        else if(score==3|score==4){
//            tempSum+=score-1;
//        }
        if(score==1|score==2){
            attractionScore-=score;
        }
        else {
            attractionScore += score;
        }
        if(attractionScore>=100){
            attractionScore=100;
        }
        int currentCommentNum=attraction.getCommentNum()+1;
        attraction.setCommentNum(currentCommentNum);
        attraction.setTempSum(tempSum);
        attraction.setScore(attractionScore);
        //attractions.save(attraction);
        customer.addAttReview(ar);
       // customers.save(customer);
        attractionReviews.save(ar);
        return ar;
    }

    public List<AttractionReview> findReviewByAtt(Long attId) {
        return attractionReviews.findAllByAttraction(attractions.findByAttractionID(attId));
    }

    public List<Attraction> findAttraction(String attraction) {
        return attractions.findAllByAttractionNameContaining(attraction);
    }

    public List<Attraction> findAttractionsByDestination(Destination destination) {
        return attractions.findAllByDestination(destination);
    }

    public Attraction findAttractionById(Long id) {
        return attractions.findByAttractionID(id);
    }
}
