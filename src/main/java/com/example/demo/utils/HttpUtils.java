package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
    public static String get(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection
                    .setRequestProperty("User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(String path) {
        // 保存整个html文档的数据
        StringBuffer html = new StringBuffer();
        try {
            // 发起一个url网址的请求
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Accept", "application/json");
            InputStream input = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                html.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html.toString();
    }
}
