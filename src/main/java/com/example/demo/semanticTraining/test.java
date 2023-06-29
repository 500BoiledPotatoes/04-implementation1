package com.example.demo.semanticTraining;

public class test {
    public static void main(String[] args) {
        NlpPipeline nlpPipeline=new NlpPipeline();
        nlpPipeline.init();
        nlpPipeline.estimatingSentiment("I do enjoy this trip, I hope I can visit here again");
//
        System.out.println(nlpPipeline.sentiment_emotion("I love this place"));

    }
}
