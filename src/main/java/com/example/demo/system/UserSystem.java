package com.example.demo.system;

import com.example.demo.model.Customer;
import com.example.demo.model.JsonResult;
import com.example.demo.model.Staff;
import com.example.demo.model.popularPlace;
import com.example.demo.services.PlanningService.BookingService;
import com.example.demo.services.UserService.CustomerService;
import com.example.demo.services.UserService.StaffService;
import com.example.demo.system.Error.ErrorType;
import com.example.demo.utils.news;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserSystem {
    private final CustomerService customerService;
    private final StaffService staffService;

    private final BookingService bookingService;
    public static int loginJudge = 0;
    public static int sloginJudge = 0;
    public static int Language = 0;
    // 0 = en, 1 = ch
    public static Customer customer;
    public static Staff staff;


    @Autowired
    public UserSystem(CustomerService customerService, StaffService staffService, BookingService bookingService) {

        this.customerService = customerService;

        this.staffService = staffService;

        this.bookingService = bookingService;
    }


    @GetMapping("/subwayPresent")
    public String toSubwayPresent(@RequestParam(name = "destinationNameCn") String destinationNameCn,
                                  HttpServletRequest request,
                                  Model model) {
        HttpSession session = request.getSession();
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("destinationNameCn", destinationNameCn);

        return "experiment";
    }



    @PostMapping("/addCustomer")
    public @ResponseBody JsonResult addCustomer(
            HttpServletRequest request,
            @RequestParam(name = "customerName") String customerName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone_number") String phone_number,
            @RequestParam(name = "pwd") String pwd,
            Model model
    ) {
        JsonResult c = customerService.addCustomer(request,customerName, phone_number, email, pwd);
        if (c.getCustomer() != null) {
            model.addAttribute("customer", c);
            return new JsonResult(0, c.getMessage());
        }else {
            return new JsonResult(1, c.getMessage());
        }
    }

    @PostMapping("/addStaff")
    public @ResponseBody JsonResult addStaff(
            @RequestParam(name = "staffName") String staffName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone_number") String phone_number,
            @RequestParam(name = "pwd") String pwd,
            HttpServletRequest request,
            Model model
    ) {
        JsonResult s = staffService.addStaff(request,staffName, phone_number, email, pwd);
        if (s.getStaff() != null) {
            model.addAttribute("staff", s.getStaff());
            return new JsonResult(0, s.getMessage());
        }else {
            return new JsonResult(1, s.getMessage());
        }
    }

    @PostMapping("/login")
    public @ResponseBody JsonResult Login(
            HttpServletRequest request,
            @RequestParam(name = "customerEmail") String customerEmail,
            @RequestParam(name = "pwd") String pwd,
            Model model
    ) {

        JsonResult c = customerService.customerLogin(request,customerEmail, pwd);
        if (c.getCustomer() == null) {
            model.addAttribute("error", new ErrorDetails(ErrorType.INVALID_ACCOUNT, "Account is not available"));
            return new JsonResult(1, c.getMessage());
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("customer",c.getCustomer());
            session.setAttribute("loginJudge",1);
            session.setAttribute("sloginJudge",0);
            model.addAttribute("loginJudge", session.getAttribute("login"));
            model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
            model.addAttribute("language", session.getAttribute("Language"));
            model.addAttribute("customer", session.getAttribute("customer"));
            staff = null;
            return new JsonResult(0, c.getMessage());
        }
    }

    @PostMapping("/slogin")
    public @ResponseBody JsonResult StaffLogin(
            HttpServletRequest request,
            @RequestParam(name = "staffEmail") String staffEmail,
            @RequestParam(name = "pwd") String pwd,
            Model model
    ) {
        JsonResult js = staffService.staffLogin(request,staffEmail, pwd);
        if (js.getStaff() == null) {
            model.addAttribute("error", new ErrorDetails(ErrorType.INVALID_ACCOUNT, "Account is not available"));
            return new JsonResult(1, js.getMessage());
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("sloginJudge",1);
            session.setAttribute("loginJudge",0);
            model.addAttribute("language", session.getAttribute("Language"));
            session.setAttribute("staff",js.getStaff());
            model.addAttribute("staff", js.getStaff());
            model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
            model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
            session.setAttribute("customer",null);
            return new JsonResult(0, js.getMessage());
        }
    }

    @GetMapping("/toClient")
    public String Client(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        session.setAttribute("bookings",null);
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        if(session.getAttribute("customer") != null) {
            model.addAttribute("customerBookings", bookingService.showBooking(((Customer) (session.getAttribute("customer"))).getCustomerId()));
        }
        return "client";
    }

    @GetMapping("/")
    public String HomePage(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        List<popularPlace>newResult=new ArrayList<>();
        popularPlace p1=new popularPlace(1,"北京","北京动物园","Beijing Zoo","Beijing","../images/1.jpeg");
        newResult.add(p1);
        popularPlace p2=new popularPlace(2,"上海","上海迪士尼度假区","Shanghai Disney Resort","Shanghai","../images/2.jpeg");
        newResult.add(p2);
        popularPlace p3=new popularPlace(3,"北京","北京环球度假区","Universal Studio Beijing","Beijing","../images/3.jpeg");
        popularPlace p4=new popularPlace(4,"武汉","武汉园博园","Wuhan Garden Expo","Wuhan","../images/4.jpeg");
        popularPlace p5=new popularPlace(5,"杭州","西湖","West Lake","Hangzhou","../images/5.jpeg");
        newResult.add(p3);
        newResult.add(p4);
        newResult.add(p5);
        popularPlace p6=new popularPlace(6,"广州","海心沙亚运公园","Haixinsha Asian Games Park","Guangzhou","../images/6.jpeg");
        newResult.add(p6);
        popularPlace p7=new popularPlace(7,"大理","洱海","Erhai Lake","Dali","../images/7.jpeg");
        newResult.add(p7);
        popularPlace p8=new popularPlace(8,"上海","外滩","The Bund","Shanghai","../images/8.jpeg");
        newResult.add(p8);
        popularPlace p9=new popularPlace(9,"成都","成都大熊猫繁育研究基地","Chengdu Research Base of Giant Panda Breeding","Chengdu","../images/9.jpeg");
        popularPlace p10=new popularPlace(10,"香港","香港迪士尼乐园","Hong Kong Disneyland","HongKong","../images/10.jpeg");
//

        popularPlace p11=new popularPlace(11,"吉安","武功山","WuGong Mountain","Jian","../images/11.jpeg");
        popularPlace p12=new popularPlace(12,"杭州","灵隐寺","Lingyin Temple","Hangzhou","../images/12.jpeg");

        popularPlace p13=new popularPlace(13,"汕头","南澳岛","Nanao Island","Shantou","../images/13.jpeg");

        popularPlace p14=new popularPlace(14,"北京","故宫","The Forbidden City","Beijing","../images/14.jpeg");

        popularPlace p15=new popularPlace(15,"北京","什刹海","Shichahai","Beijing","../images/15.jpeg");
        newResult.add(p9);
        newResult.add(p10);
        newResult.add(p11);
        newResult.add(p12);
        newResult.add(p13);
        newResult.add(p14);
        newResult.add(p15);


        model.addAttribute("popularPlaces",newResult);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("staff", session.getAttribute("staff"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "Trueindex";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        if(session.getAttribute("Language")==null){
            session.setAttribute("Language",0);
        }
        session.setAttribute("Language",((int)(session.getAttribute("Language")) + 1 ) % 2);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("staff", session.getAttribute("staff"));
        return "redirect:/";
    }



    @GetMapping("/Logout")
    public String Logout(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        session.setAttribute("loginJudge",null);
        session.setAttribute("sloginJudge",null);
        session.setAttribute("customer",null);
        session.setAttribute("language",null);
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "redirect:/";
    }


    @GetMapping("/toLogin")
    public String Login(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "login";
    }


    @GetMapping("/tosLogin")
    public String sLogin(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("staff", session.getAttribute("staff"));
        return "slogin";
    }


    @GetMapping("/toContact")
    public String Contact(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "Contact";
    }


    @GetMapping("/toReadMore")
    public String ReadMore(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "ReadMore";
    }

    @GetMapping("/staff")
    public String Staff(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "staff";
    }


    @GetMapping("/staffAddAcc")
    public String StaffAcc(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffAddAcc";
    }

    @GetMapping("/staffAddDes")
    public String StaffDes(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffAddDes";
    }


    @GetMapping("/staffAddAtt")
    public String StaffAtt(
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "StaffAddAtt";
    }


    @PostMapping("/findCustomer")
    public String findCustomerByName(
            HttpServletRequest request,
            @RequestParam(name = "customerName") String name,
            Model model
    ) {
        List<Customer> customers = customerService.findCustomerByName(name);
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        model.addAttribute("customers", customers);
        return "CustomersList";
    }


    @GetMapping("/CustomersList")
    public String toCustomersList(
            HttpServletRequest request,
            Model model
    ) {

        List<Customer> customers = customerService.findCustomerByName("");
        model.addAttribute("customers", customers);
        HttpSession session = request.getSession();
        model.addAttribute("loginJudge", session.getAttribute("loginJudge"));
        model.addAttribute("sloginJudge", session.getAttribute("sloginJudge"));
        model.addAttribute("language", session.getAttribute("Language"));
        return "CustomersList";
    }

    @PostMapping("/leaveMsg")
    public @ResponseBody JsonResult leaveMsg(@RequestParam(name = "username") String name,
                                             @RequestParam(name = "email") String email,
                                             @RequestParam(name = "phone") String phoneNum,
                                             @RequestParam(name = "subject") String topic,
                                             @RequestParam(name = "message") String content,
                                             Model model){
        JsonResult js=customerService.leaveMsg(name,email,phoneNum,topic,content);
        return js;

    }


}
