package com.example.demo.services.PlanningService;

import com.example.demo.model.*;
import com.example.demo.repositories.AccommodationRepository;
import com.example.demo.repositories.AccommodationReviewRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.DestinationRepository;
import com.example.demo.system.UserSystem;
import com.example.demo.semanticTraining.NlpPipeline;
import org.springframework.stereotype.Service;
import com.example.demo.model.JsonResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;

@Service
public class AccommodationService {
    private final AccommodationRepository accommodations;
    private final AccommodationReviewRepository accommodationReviews;

    private final CustomerRepository customers;
    private final DestinationRepository destinations;

    private NlpPipeline nlpPipeline;

    public AccommodationService(AccommodationRepository a, AccommodationReviewRepository accommodationReviews, CustomerRepository customers, DestinationRepository destinations) {
        this.accommodations = a;
        this.accommodationReviews = accommodationReviews;
        this.customers = customers;
        this.destinations = destinations;
        nlpPipeline=new NlpPipeline();
        nlpPipeline.init();
    }


    public JsonResult addAccommodation(HttpServletRequest httpServletRequest,String accommodationName, String accommodationNameCn, String accommodationIntro, String accommodationIntroCn, String address, String addressCn, String destination, String filePath) {
        String message = "";
        if (!accommodationName.trim().equals("") && !accommodationIntro.trim().equals("") && !accommodationNameCn.trim().equals("") && !accommodationIntroCn.trim().equals("")) {
            if (accommodations.findByAccommodationName(accommodationName) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Accommodation Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Accommodation Already Exist";
                }else {
                    message = "该住处已经存在";
                }
                return new JsonResult(1,message,(Accommodation)null);
            } else {
                Destination de = destinations.findByDestinationName(destination);
                if (de != null) {
                    Accommodation d = new Accommodation(accommodationName, accommodationNameCn,accommodationIntro,accommodationIntroCn, address,addressCn, de, filePath);
                    accommodations.save(d);
                    de.addAccommodations(d);
                    destinations.save(de);
                    if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                        message = "Accommodation Add Successfully";
                    }
                    else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                        message = "Accommodation Add Successfully";
                    }else {
                        message = "该住处成功添加";
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
                    return new JsonResult(1,message,(Accommodation) null);
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
            return new JsonResult(1, message, (Accommodation) null);
        }
    }

    public JsonResult addAccReview(HttpServletRequest httpServletRequest,Long cid, String content, Long accid) {
        String message = "";
        Customer customer = customers.findByCustomerId(cid);
        if(customer == null){
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "Please Login first";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "Please Login first";
            }else {
                message = "请先登录";
            }
            return new JsonResult(1,message,(AccommodationReview) null);
        }
        Accommodation accommodation = accommodations.findByAccommodationId(accid);
        int score = nlpPipeline.estimatingSentiment(content);
        LocalDateTime localDateTime = LocalDateTime.now(); // 获取当前时间
        int year = localDateTime.getYear(); // 获取年份
        int month = localDateTime.getMonthValue(); // 获取月份
        int day = localDateTime.getDayOfMonth(); // 获取月中的天数
        int hour = localDateTime.getHour(); // 获取当前的小时
        int minute = localDateTime.getMinute(); // 获取当前分钟
        int second = localDateTime.getSecond(); // 获取当前秒数
        LocalDateTime time = LocalDateTime.of(year, Month.of( month ), day ,hour,minute,second);

        int commentNum=accommodation.getCommentNum();
        int AccomScore=accommodation.getScore();
        int attractionScore=accommodation.getScore();
        int tempSum=accommodation.getTempSum();
        if(score==1|score==2){
            attractionScore-=score;
        }
        else {
            attractionScore += score;
        }
        if(attractionScore>=100){
            attractionScore=100;
        }
        int currentCommentNum=accommodation.getCommentNum()+1;
        accommodation.setCommentNum(currentCommentNum);
        accommodation.setTempSum(tempSum);
        accommodation.setScore(attractionScore);
//        if(currentCommentNum%10==0&&currentCommentNum!=0){
//            int value=tempSum/10;
//            attractionScore+=value;
//            tempSum=0;
//            accommodation.setScore(attractionScore);
//            accommodation.setTempSum(0);
//        }
        AccommodationReview ar = new AccommodationReview(time, customer, content,  score, accommodation);
        accommodationReviews.save(ar);
        accommodation.addReview(ar);
        accommodations.save(accommodation);
        customer.addAccReview(ar);
        customers.save(customer);
        System.out.println(ar.toString());
        if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
            message = "AccommodationReview add successfully";
        }
        else
        if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
            message = "AccommodationReview add successfully";
        }else {
            message = "评论发表成功";
        }
        return  new JsonResult(0,message,ar);
    }
    public JsonResult editAcc(HttpServletRequest httpServletRequest, Long aid , String accommodationName, String accommodationNameCn, String accommodationIntro,String accommodationIntroCn, String accommodationAddress,String accommodationAddressCn){
        Accommodation b = accommodations.findByAccommodationId(aid);
        String message = "";
        if (!Objects.equals(accommodationName, b.getAccommodationName()) && !accommodationName.trim().equals("")) {
            if (accommodations.findByAccommodationName(accommodationName) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Accommodation Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Accommodation Already Exist";
                }else {
                    message = "该住宿已经存在";
                }
                return new JsonResult(1, message,  (Accommodation) null);
            } else {
                b.setAccommodationName(accommodationName );
            }
        }
        if (!Objects.equals(accommodationNameCn, b.getAccommodationNameCn()) && !accommodationNameCn.trim().equals("")) {
            if (accommodations.findByAccommodationNameCn(accommodationNameCn) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Accommodation Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Accommodation Already Exist";
                }else {
                    message = "该住宿已经存在";
                }
                return new JsonResult(1, message, (Accommodation) null);
            } else {
                b.setAccommodationNameCn(accommodationNameCn);
            }
        }
        if (!Objects.equals(accommodationIntro, b.getAccommodationIntro()) && !accommodationIntro.trim().equals("")) {
            b.setAccommodationIntro(accommodationIntro);
        }
        if (!Objects.equals(accommodationIntroCn, b.getAccommodationIntroCn()) && !accommodationIntroCn.trim().equals("")) {
            b.setAccommodationIntroCn(accommodationIntroCn);
        }
        if (!Objects.equals(accommodationAddress, b.getAccommodationAddress()) && !accommodationAddress.trim().equals("")) {
            b.setAccommodationAddress(accommodationAddress);
        }
        if (!Objects.equals(accommodationAddressCn, b.getAccommodationAddressCn()) && !Objects.equals(accommodationAddressCn, "")) {
            b.setAccommodationAddressCn(accommodationAddressCn);
        }
        accommodations.save(b);
        System.out.println("22222222222222222222222");
        return new JsonResult(0, "edit successfully", b);
    }

    public JsonResult deleteAccommodation(Long aid){
        Accommodation accommodation=accommodations.findByAccommodationId(aid);
        accommodations.delete(accommodation);
        return new JsonResult(0,"delete successfully",accommodation);
    }



    public  List<AccommodationReview> findReviewByAcc(Long accommodation){
        return accommodationReviews.findAllByAccommodation(accommodations.findByAccommodationId(accommodation));
    }
    public List<Accommodation> findAccommodation(String accommodation) {
        return accommodations.findAllByAccommodationNameContaining(accommodation);
    }

    public List<Accommodation> findAccommodationByDestination(Destination id) {
        return accommodations.findAllByDestination(id);
    }

    public Accommodation findAccommodationById(Long id) {
        return accommodations.findByAccommodationId(id);
    }
    public JsonResult editAccommodation(HttpServletRequest httpServletRequest,Long id, String accommodationName, String accommodationNameCn, String accommodationIntro, String accommodationIntroCn, String address, String addressCn, String filePath) {
        String message = "";
        Accommodation a = accommodations.findByAccommodationId(id);
        if (!accommodationName.trim().equals("")) {
            if (accommodations.findByAccommodationName(accommodationName) != null) {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Accommodation Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Accommodation Already Exist";
                }else {
                    message = "该住处已经存在";
                }
                return new JsonResult(1,message,(Accommodation)null);
            } else {
                a.setAccommodationName(accommodationName);

            }
        }
        if (!accommodationNameCn.trim().equals("")){
            if (accommodations.findByAccommodationNameCn(accommodationNameCn) != null){
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Accommodation Already Exist";
                }
                else if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Accommodation Already Exist";
                }else {
                    message = "该住处已经存在";
                }
                return new JsonResult(1,message,(Accommodation)null);
            }
            else{
                a.setAccommodationNameCn(accommodationNameCn);
            }
        }
        if (!accommodationIntro.trim().equals("")){
            a.setAccommodationIntro(accommodationIntro);
        }
        if (!accommodationIntroCn.trim().equals("")){
            a.setAccommodationIntroCn(accommodationIntroCn);
        }
        if (!address.trim().equals("")){
            a.setAccommodationAddress(address);
        }
        if (!addressCn.trim().equals("")){
            a.setAccommodationAddressCn(addressCn);
        }
        if (((int)(httpServletRequest.getSession().getAttribute("Language"))) == 1){
            message = "修改成功";
        }else {
            message = "successfully edited";
        }
        accommodations.save(a);
        return new JsonResult(0, message);
    }

}
