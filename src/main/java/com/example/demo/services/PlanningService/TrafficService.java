package com.example.demo.services.PlanningService;

import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import com.example.demo.model.JsonResult;
import com.example.demo.model.Traffic;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.TrafficRepository;
import com.example.demo.system.UserSystem;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrafficService {
    private final TrafficRepository trafficRepository;

    private final CustomerRepository customerRepository;

    public TrafficService(TrafficRepository trafficRepository, CustomerRepository customerRepository) {
        this.trafficRepository = trafficRepository;
        this.customerRepository = customerRepository;
    }

    public JsonResult addTraffic(HttpServletRequest httpServletRequest, String type, String typeCn, String from, String fromCn, String to, String toCn,LocalDateTime start, LocalDateTime end, String price) {
        String message = "";
        HttpSession session=httpServletRequest.getSession();
        if (start != null && end != null && !end.isAfter(start)) {
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "The times you input are wrong, please write again";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "The times you input are wrong, please write again";
            } else {
                message = "时间填写有误,请重新填写字段";
            }
            return new JsonResult(1, message, (Traffic) null);
        } else if (!type.trim().isEmpty() && !from.trim().isEmpty() && !to.trim().isEmpty() && start != null && end != null && !price.trim().isEmpty()) {
            Traffic traffic = new Traffic(type, typeCn,start, end, from, to,fromCn, toCn,price);
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "Add successfully";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "Add successfully";
            } else {
                message = "成功添加";
            }
            trafficRepository.save(traffic);
            return new JsonResult(0, message, traffic);
        } else {
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "please fill all the fields";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "please fill all the fields";
            } else {
                message = "请填写所有字段";
            }
            return new JsonResult(1, message, (Traffic) null);
        }


    }
    public List<Traffic> findByType(String type){
        return trafficRepository.findAllByTypeContaining(type);
    }

    public JsonResult addBooking(HttpServletRequest httpServletRequest,Long cid, Long tid){
        String message = "add successfully";
        Customer c = customerRepository.findByCustomerId(cid);
        Traffic t = trafficRepository.findByTrafficId(tid);
        c.addTraffic(t);
        t.addCustomer(c);
        customerRepository.save(c);
        trafficRepository.save(t);
//        if ((int)(httpServletRequest.getSession().getAttribute("language")) == 0) {
//            message = "add successfully ";
//        } else {
//            message = "成功添加";
//        }
        return new JsonResult(0, message);
    }

    public JsonResult deleteBooking(Long cid, Long tid){
        Traffic t = trafficRepository.findByTrafficId(tid);
        Customer c = customerRepository.findByCustomerId(cid);
        c.deleteTraffic(t);
        t.deleteCus(c);
        customerRepository.save(c);
        trafficRepository.save(t);
        return new JsonResult(0, "delete successfully");
    }

}
