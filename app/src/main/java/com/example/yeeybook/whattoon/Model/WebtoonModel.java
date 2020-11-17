package com.example.yeeybook.whattoon.Model;

public class WebtoonModel {
    public String author;
    public String day;
    public String platform;
    public String story;
    public String genre;
    public String title;
    public String url;
    public String keyword;
    public int webtoonId;
    public int favorite;

    public WebtoonModel(){

    }

    public WebtoonModel(String author, String day, String platform, String story, String title, int webtoonId, int favorite,String genre,String url, String keyword) {
        this.author = author;
        this.day = day;
        this.platform = platform;
        this.story = story;
        this.title = title;
        this.webtoonId = webtoonId;
        this.favorite = favorite;
        this.genre = genre;
        this.url = url;
        this.keyword = keyword;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWebtoonId() {
        return webtoonId;
    }

    public void setWebtoonId(int webtoonId) {
        this.webtoonId = webtoonId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
