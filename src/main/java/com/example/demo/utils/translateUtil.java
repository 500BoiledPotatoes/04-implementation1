package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class translateUtil {
    private static final String APP_ID = "20220406001160362";
    private static final String SECURITY_KEY = "dyP1zzuDSiyYwZPeTmF0";
    public String translate(String str){
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String result=api.getTransResult(str,"zh","en");
        int begin = result.indexOf("dst");
        String en = result.substring(begin + 6, result.length() - 4);
        return en;
    }



}
