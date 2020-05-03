package com.proj.mausoleum.pojo;

public class Picture {
    private Integer pictureId;
    private Integer mInfoId;
    private String alt;
    private String src;

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getmInfoId() {
        return mInfoId;
    }

    public void setmInfoId(Integer mInfoId) {
        this.mInfoId = mInfoId;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
