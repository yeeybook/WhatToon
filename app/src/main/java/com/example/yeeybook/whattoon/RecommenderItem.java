package com.example.yeeybook.whattoon;

public class RecommenderItem {
    private String title;
    private float sim;

    public String getTitle(){
        return title;
    }
    public float getSim(){
        return sim;
    }
    public void setTitle(String s){
        title = s;
    }
    public void setSim(float s){
        sim = s;
    }
}
