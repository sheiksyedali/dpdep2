package com.ecomshop.deskplus.services.chat;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Author: Sheik Syed Ali
 * Date: 03 Nov 2021
 */
public class SocketTextHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        String payload = message.getPayload();
        JSONObject msg = new JSONObject(payload);
        MessageBin.getInstance().incoming(msg, session);

//        System.out.println("shek: "+payload);
//        session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
//        UseAndThrowContainer.getInstance().add(new ChatTemp(session, jsonObject));

    }
}
