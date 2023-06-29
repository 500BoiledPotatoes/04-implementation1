package com.example.demo.semanticTraining;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class NlpPipeline {
    StanfordCoreNLP pipeline = null;
    int score;

//    initialize
    public  void init()
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public String sentiment_emotion(String text)
    {
        int emotion;
        this.score = 0;
        String emotion_state;
        String str="";
        //传入我们需要分析的字符串
        Annotation annotation = pipeline.process(text);
        int i=0;
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {   //语法树
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            //情感打分
            emotion = RNNCoreAnnotations.getPredictedClass(tree);
            i++;
            score+=emotion;
            //情感状态
            emotion_state = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            str +=emotion_state + ": "  + sentence+ " "+emotion +"|";

        }
        score = score/i;
        return str;
    }

    public int estimatingSentiment(String text)
    {
        int sentimentInt;
        String sentimentName;
        Annotation annotation = pipeline.process(text);
        int sumScore=0;
        int sentenceCount=0;
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            sentenceCount++;
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            sumScore+=sentimentInt;
            System.out.println(sentimentName + "\t" + sentimentInt + "\t" + sentence);
        }

        return Math.round((float)sumScore/(float) sentenceCount);
    }

    public void great() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        // gender,lemma,ner,parse,pos,sentiment,sspplit, tokenize
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable

        String sentimentText = "I like this place very much! I would like to invite my friend to come here with me next time";
        String[] ratings = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        Annotation annotation = pipeline.process(sentimentText);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.println("sentence:'" + sentence + "' has a score of" + (score - 2) + " rating:" + ratings[score]);
            System.out.println(tree);
        }
    }
}
