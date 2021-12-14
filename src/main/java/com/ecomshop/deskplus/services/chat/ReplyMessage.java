package com.ecomshop.deskplus.services.chat;

import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.Map;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class ReplyMessage {
    private String regId;
    private Long userId;
    private String userName;
    private String rawMessage;
    private String trackId;
    private Date time;
    private String sourceId;
    private Map<String, String> messageThread;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Map<String, String> getMessageThread() {
        return messageThread;
    }

    public void setMessageThread(Map<String, String> messageThread) {
        this.messageThread = messageThread;
    }
}
