package com.proj.mausoleum.pojo;

public class MInfo {
    private Integer mInfoId;
    private String name;
    private String details;
    private String cover;
    private String lastAuditUsername;
    private String lastAuditTime;
    private Integer coordinateId;

    public Integer getmInfoId() {
        return mInfoId;
    }

    public void setmInfoId(Integer mInfoId) {
        this.mInfoId = mInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLastAuditUsername() {
        return lastAuditUsername;
    }

    public void setLastAuditUsername(String lastAuditUsername) {
        this.lastAuditUsername = lastAuditUsername;
    }

    public String getLastAuditTime() {
        return lastAuditTime;
    }

    public void setLastAuditTime(String lastAuditTime) {
        this.lastAuditTime = lastAuditTime;
    }

    public Integer getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(Integer coordinateId) {
        this.coordinateId = coordinateId;
    }
}
