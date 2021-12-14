package com.ecomshop.deskplus.web.rest;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class ReplyRequest {
    private String regUid;
    private String userName;
    private Long userId;
    private String trackId;
    private String message;
    private String sourceId;

    public String getRegUid() {
        return regUid;
    }

    public void setRegUid(String regUid) {
        this.regUid = regUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
