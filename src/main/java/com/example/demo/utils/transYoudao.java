package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;

public class transYoudao {
    String requestUrl="https://fanyi.youdao.com/translate?&doctype=json&type=en&i=";
    public String translate(String str){
        if (str=="香港"){
            return "HongKong";
        }
        if (str=="橘子洲"){
            return "Orange Isle";
        }
        String url=requestUrl+str;
        String html=HttpUtils.get(url);
        JSONObject js=JSONObject.parseObject(html);
        String transResult=js.getString("translateResult");
        int index=transResult.indexOf("tgt");
        String substring=transResult.substring(2,transResult.length()-2);
        JSONObject js2=JSONObject.parseObject(substring);
        String rersult=js2.getString("tgt");
        return rersult;
    }


}
