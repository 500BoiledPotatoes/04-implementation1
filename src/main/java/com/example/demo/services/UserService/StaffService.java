package com.example.demo.services.UserService;

import com.example.demo.model.Customer;
import com.example.demo.model.JsonResult;
import com.example.demo.model.Staff;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.StaffRepository;
import com.example.demo.system.UserSystem;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class StaffService {
    private final StaffRepository staffs;

    public StaffService(StaffRepository staffs) {
        this.staffs = staffs;
    }


    public JsonResult addStaff(HttpServletRequest httpServletRequest,String name, String phoneNumber, String email, String pwd) {
        String message = "";
        if (!name.trim().equals("")
                && !phoneNumber.trim().equals("")
                && !email.trim().equals("")
                && !pwd.trim().equals("")) {
            // if customer input is not empty
            Staff s = staffs.findByEmail(email);
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
                return new JsonResult(1, message, (Staff) null);
            } else {
                Staff staff = new Staff(name, phoneNumber, email, pwd);
                staffs.save(staff);
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Successfully Registered";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Successfully Registered";
                }else {
                    message = "注册成功";
                }
                return new JsonResult(0, message, staff);
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

    public JsonResult staffLogin(HttpServletRequest httpServletRequest,String Email, String pwd){
        String message = "";
        if(!Email.trim().equals("")&&!pwd.trim().equals("")) {
            Staff s = staffs.findByEmail(Email);
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
            }else if (!s.getPwd().equals(pwd)){
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Incorrect Password";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Incorrect Password";
                }else {
                    message = "密码错误";
                }
                return new JsonResult(1, message, (Staff) null);
            }else {
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Login Successfully";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Login Successfully";
                }else {
                    message = "登陆成功";
                }
                return new JsonResult(0, message, s);
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
            return new JsonResult(1, message, (Staff) null);
        }
    }
}
