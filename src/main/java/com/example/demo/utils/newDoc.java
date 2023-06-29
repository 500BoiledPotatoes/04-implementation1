package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.popularPlace;

import java.util.ArrayList;

public class newDoc {
    public static void main(String[] args) {
        String url="https://car.weibo.com/interface/travel/chaowurank?type=1&date=&list_type=scenic&city=%E5%85%A8%E5%9B%BD&limit=20&from_action=bangdan_list";
        String html=HttpUtils.get(url);
        JSONObject json= JSON.parseObject(html);
        JSONObject data=  json.getJSONObject("data");
        JSONArray data2=data.getJSONArray("list");
        ArrayList<popularPlace> arrayList=new ArrayList<>();
        int rank=1;
        for(Object term:data2.subList(0,20)){
            JSONObject js=JSON.parseObject(term.toString());
            String name=js.getString("name");
            String city=js.getString("city");
            int orderNum= rank;
            rank++;
            String imageLink=js.getString("image");
            String name_en= "";
            String city_en="";
            popularPlace p=new popularPlace(orderNum,city,name,name_en,city_en,imageLink);
            arrayList.add(p);
        }
        for (popularPlace p:arrayList){
            System.out.println(p.toString());
        }
    }
}
