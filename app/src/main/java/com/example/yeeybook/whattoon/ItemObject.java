package com.example.yeeybook.whattoon;


public class ItemObject {

    private String content;
    private int imageId;

    public ItemObject(String content, int imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
