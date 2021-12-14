package com.ecomshop.deskplus.web.rest;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class UpdateChatRequest {
    private String regUid;
    private Long userId;
    private String trackId;
    private String status;

    public String getRegUid() {
        return regUid;
    }

    public void setRegUid(String regUid) {
        this.regUid = regUid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
