package com.example.yeeybook.whattoon;

public class ItemData {

    private String nn;
    private int imgid;
    private String desc;
    private int sn;

    public ItemData(String nn, int imgid, String desc, int sn) {
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

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }
}
