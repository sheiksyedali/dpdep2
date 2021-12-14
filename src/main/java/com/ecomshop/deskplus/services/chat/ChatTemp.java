package com.ecomshop.deskplus.services.chat;

import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;

/**
 * Author: Sheik Syed Ali
 * Date: 03 Nov 2021
 */
public class ChatTemp{
    private WebSocketSession webSocketSession;
    private JSONObject data;

    public ChatTemp(WebSocketSession webSocketSession, JSONObject data){
        this.webSocketSession = webSocketSession;
        this.data = data;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}