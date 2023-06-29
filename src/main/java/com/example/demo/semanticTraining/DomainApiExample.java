//package com.example.demo.semanticTraining;
//
//
//import com.cloudmersive.client.AnalyticsApi;
//import com.cloudmersive.client.invoker.*;
//import com.cloudmersive.client.invoker.auth.*;
//import com.cloudmersive.client.model.*;
//import com.cloudmersive.client.DomainApi;
//
//import java.io.File;
//import java.util.*;
//
//public class DomainApiExample {
//
//    public static void main(String[] args) {
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//// Configure API key authorization: Apikey
//        ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
//        Apikey.setApiKey("d5c739c6-db6c-489f-b5eb-799b1a953cc9");
//// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
////Apikey.setApiKeyPrefix("Token");
//        AnalyticsApi apiInstance = new AnalyticsApi();
//        String comment="shit place, never come here again";
//        SentimentAnalysisRequest sentimentAnalysisRequest=new SentimentAnalysisRequest().textToAnalyze(comment);// SentimentAnalysisRequest | Input sentiment analysis request
//        try {
//            System.out.println(sentimentAnalysisRequest.toString());
//            SentimentAnalysisResponse result = apiInstance.analyticsSentiment(sentimentAnalysisRequest);
//            System.out.println(result.getSentimentClassificationResult());
//            System.out.println(result.getSentimentScoreResult());
//        } catch (ApiException e) {
//            System.err.println("Exception when calling AnalyticsApianalyticsSentiment");
//            e.printStackTrace();
//        }
//    }
//}
