package com.ecomshop.deskplus.web.rest;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class ChatRequest {
    private String regUid;
    private String customerName;
    private String email;
    private String trackId;
    private String message;
    private String sourceId;

    public String getRegUid() {
        return regUid;
    }

    public void setRegUid(String regUid) {
        this.regUid = regUid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
