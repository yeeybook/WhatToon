package com.example.yeeybook.whattoon;

public class ItemData {

    private String nn;
    private String imgid;
    private String desc;
    private float sn;

    public ItemData(String nn, String imgid, String desc, float sn) {
        this.nn = nn;
        this.imgid = imgid;
        this.desc = desc;
        this.sn = sn;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getSn() {
        return sn;
    }

    public void setSn(float sn) {
        this.sn = sn;
    }
}
