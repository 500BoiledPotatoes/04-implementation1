package com.example.demo.services.UserService;

import com.example.demo.model.Customer;
import com.example.demo.model.JsonResult;
import com.example.demo.model.MsgLeave;
import com.example.demo.model.Staff;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.system.UserSystem;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customers;

    public CustomerService(CustomerRepository customers) {
        this.customers = customers;
    }

    public JsonResult addCustomer (HttpServletRequest httpServletRequest,String name, String phoneNumber, String email, String pwd) {
        String message = "";
        if (!name.trim().equals("")
                && !phoneNumber.trim().equals("")
                && !email.trim().equals("")
                && !pwd.trim().equals("")) {
            // if customer input is not empty
            Customer s = customers.findByEmail(email);
            if (s != null) {
                // customer exist
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Account Already Exist";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Account Already Exist";
                }else {
                    message = "该邮箱已经注册";
                }
                return new JsonResult(1, message, (Customer) null);
            } else {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Successfully Registered";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Successfully Registered";
                }else {
                    message = "注册成功";
                }
                Customer c = new Customer(name,phoneNumber,email,pwd);
                customers.save(c);
                return new JsonResult(0, message);
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
            return new JsonResult(1, message, (Staff) null);
        }
    }
    public JsonResult customerLogin(HttpServletRequest httpServletRequest,String Email, String pwd){
        String message = "";

        if(!Email.trim().equals("")&&!pwd.trim().equals("")) {
            Customer s = customers.findByEmail(Email);
            if (s == null) {
                System.out.println(1);
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "No Such Account Found";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "No Such Account Found";
                }else {
                    message = "用户不存在";
                }
                return new JsonResult(1, message, s);
            }else {
                if(pwd.equals(s.getPwd())) {
                    if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                        message = "Login Successfully";
                    } else if ((int) (httpServletRequest.getSession().getAttribute("Language")) != 1) {
                        message = "Login Successfully";
                    } else {
                        message = "登陆成功";
                    }
                    return new JsonResult(0, message, s);
                }else {
                    if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                        message = "Wrong password";
                    } else if ((int) (httpServletRequest.getSession().getAttribute("Language")) != 1) {
                        message = "Wrong password";
                    } else {
                        message = "密码错误";
                    }
                    return new JsonResult(1, message);
                }
            }
        }else {
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "Please Enter Your Email and Password";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "Please Enter Your Email and Password";
            }else {
                message = "请填写密码和邮箱";
            }
            return new JsonResult(1, message, (Customer) null);
        }
    }


    public List<Customer> findCustomerByName(String name){
        List<Customer> customer = customers.findAllByNameContaining(name);
        return customer;
    }
    public Customer findCustomerById(Long id){
        Customer customer = customers.findByCustomerId(id);
        return customer;
    }

    public JsonResult leaveMsg(String name,String Email,String phoneNumber,String title, String content){
        String msgAjax="";
        if (name.trim().isEmpty()){
            msgAjax="please enter your name";
            return new JsonResult(1,msgAjax,(MsgLeave) null);
        }
        if(Email.trim().isEmpty()){
            msgAjax="please enter your email";
            return new JsonResult(1,msgAjax,(MsgLeave) null);
        }
        if (phoneNumber.isEmpty()){
            msgAjax="please enter your phoneNumber";
            return new JsonResult(1,msgAjax,(MsgLeave) null);
        }
        if(title.trim().isEmpty()){
            msgAjax="title shouldn't be empty";
            return new JsonResult(1,msgAjax,(MsgLeave) null);
        }
        if (content.trim().isEmpty()){
            msgAjax="content shouldn't be null";
            return new JsonResult(1,msgAjax,(MsgLeave) null);
        }
        if(!name.trim().isEmpty()&&!Email.trim().isEmpty()&&!phoneNumber.trim().isEmpty()&&!title.trim().isEmpty()&&!content.trim().isEmpty()){
            MsgLeave msg=new MsgLeave(name,Email,phoneNumber,title,content);
            msgAjax="the message has left successfully.";
            return new JsonResult(0,msgAjax,msg);
        }
        return null;
    }

}
