package com.example.yeeybook.whattoon;

public class WebtoonSample {

    private int id;
    private String title;
    private String day;
    private String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay(){
        return day;
    }
    public void setDay(String day){
        this.day =day;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "WebtoonSample{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", day='" + day + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

}
