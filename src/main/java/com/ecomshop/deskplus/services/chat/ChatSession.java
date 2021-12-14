package com.ecomshop.deskplus.services.chat;

import org.springframework.web.socket.WebSocketSession;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class ChatSession {
    private String trackId;
//    private String customerId;
//    private Long userId;
    private WebSocketSession customerSession;
    private WebSocketSession userSession;

    public WebSocketSession getCustomerSession() {
        return customerSession;
    }

    public void setCustomerSession(WebSocketSession customerSession) {
        this.customerSession = customerSession;
    }

    public WebSocketSession getUserSession() {
        return userSession;
    }

    public void setUserSession(WebSocketSession userSession) {
        this.userSession = userSession;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(String customerId) {
//        this.customerId = customerId;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
}
