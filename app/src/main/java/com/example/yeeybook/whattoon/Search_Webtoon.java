package com.example.yeeybook.whattoon;

public class Search_Webtoon {


    public String image,title,author;
    public int webtoonId;

    public Search_Webtoon() {
    }

    public Search_Webtoon(String image, String title, String author, int webtoonId) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.webtoonId = webtoonId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWebtoonId() {
        return webtoonId;
    }

    public void setWebtoonId(int webtoonId) {
        this.webtoonId = webtoonId;
    }
}
