package com.ecomshop.deskplus.services.chat;

import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.Map;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class ChatMessage {
    private String regUid;
    private String customerName;
    private String customerEmail;
    private Map<String, String> messageThread;
    private String rawMessage;
    private Date time;
    private String trackId;
    private String sourceId;
    private boolean isNew;

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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Map<String, String> getMessageThread() {
        return messageThread;
    }

    public void setMessageThread(Map<String, String> messageThread) {
        this.messageThread = messageThread;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
