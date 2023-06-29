package com.example.demo.services.PlanningService;

import com.example.demo.model.Attraction;
import com.example.demo.model.Destination;
import com.example.demo.model.JsonResult;
import com.example.demo.repositories.DestinationRepository;
import com.example.demo.system.UserSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class DestinationService {
    private final DestinationRepository destinations;

    public DestinationService(DestinationRepository a){
        this.destinations = a;
    }

    public JsonResult addDestination(HttpServletRequest httpServletRequest,String destinationName, String destinationNameCn, String destinationIntro, String destinationIntroCn, String imgPath){
       String message = "";
        if (!destinationName.trim().equals("")
                && !destinationIntro.trim().equals("")
                &&!destinationNameCn.trim().equals("")
                && !destinationIntroCn.trim().equals("")
        ){
            if (destinations.findByDestinationName(destinationName) != null){
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Destination Already Exist";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Destination Already Exist";
                }else {
                    message = "该目的地已经存在";
                }
                return new JsonResult(1,message,(Destination) null);
            }else {
                Destination d = new Destination(destinationName,destinationNameCn, destinationIntro,destinationIntroCn,imgPath);
                destinations.save(d);
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Destination Add Successfully";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Destination Add Successfully";
                }else {
                    message = "该目的地成功添加";
                }
                return new JsonResult(0,message,d);
            }
        }else {
            if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                message = "Please Fill All the Input Fields";
            }
            else
            if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                message = "Please Fill All the Input Fields";
            }else {
                message = "请填写全部内容";
            }
            return new JsonResult(1, message, (Destination) null);
        }
    }


    public JsonResult editDestination(HttpServletRequest httpServletRequest,Long did, String destinationName, String destinationNameCn, String destinationIntro, String destinationIntroCn){
        String message = "";
        Destination d = destinations.findByDestinationId(did);
        if (!Objects.equals(destinationName, d.getDestinationName()) && !destinationName.trim().equals(""))
            if (destinations.findByDestinationName(destinationName) != null){
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Destination Already Exist";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Destination Already Exist";
                }else {
                    message = "该目的地已经存在";
                }
                return new JsonResult(1,message,(Destination) null);
            }else {
                d.setDestinationName(destinationName);
            }
        if (!Objects.equals(destinationNameCn, d.getDestinationNameCn()) && !destinationNameCn.trim().equals("")){
            if (destinations.findByDestinationNameCn(destinationNameCn) != null){
                if ((httpServletRequest.getSession().getAttribute("Language")) == null) {
                    message = "Destination Already Exist";
                }
                else
                if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
                    message = "Destination Already Exist";
                }else {
                    message = "该目的地已经存在";
                }
                return new JsonResult(1,message,(Destination) null);
            }else {
                d.setDestinationName(destinationNameCn);
            }
        }
        if (!Objects.equals(destinationIntro, d.getDestinationIntro()) && !destinationIntro.trim().equals("")){
            d.setDestinationIntro(destinationIntro);
        }
        if (!Objects.equals(destinationIntroCn, d.getDestinationIntro()) && !destinationIntroCn.trim().equals("")){
            d.setDestinationIntro(destinationIntroCn);
        }
        if ((int)(httpServletRequest.getSession().getAttribute("Language")) != 1) {
            message = "Destination Edit Successfully";
        }else {
            message = "成功修改";
        }
        destinations.save(d);
        return new JsonResult(0, message);

    }

    public List<Destination> findDestination(String destination){
        return destinations.findAllByDestinationNameContaining(destination);
    }

    public Destination findDestinationById(Long id){
        return destinations.findByDestinationId(id);
    }


    public Page<Destination> findPageDestination(String name, Pageable pageable){
        return destinations.findAllByDestinationNameContaining(name,pageable);
    }

    public Page<Destination>findDesPaginated(int pageNumber,int pageSize){
        Pageable pageable=PageRequest.of(pageNumber-1,pageSize);
        return destinations.findAll(pageable);
    }

    public Page<Destination> findDesPaginatedByWord(String keyword,int pageNumber,int pageSize){
        Pageable pageable=PageRequest.of(pageNumber-1,pageSize);
        return destinations.findAllByDestinationNameContaining(keyword,pageable);
    }


}
