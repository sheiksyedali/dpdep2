package com.ecomshop.deskplus.services.chat;

import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Stack;

/**
 * Author: Sheik Syed Ali
 * Date: 03 Nov 2021
 */
public class UseAndThrowContainer {
    private static UseAndThrowContainer useAndThrowContainer;

    private Stack<ChatTemp> dustBin;

    private UseAndThrowContainer(){
        dustBin = new Stack();
    }

    public void check(String reply){
        try {
            ChatTemp chatTemp = dustBin.pop();
            JSONObject data = chatTemp.getData();
            WebSocketSession webSocketSession = chatTemp.getWebSocketSession();
            String payload = data.get("user")+": " + reply;
            webSocketSession.sendMessage(new TextMessage(payload));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void add(ChatTemp chatTemp){
        dustBin.add(chatTemp);
    }

    public static UseAndThrowContainer getInstance(){
        return useAndThrowContainer == null ? useAndThrowContainer = new UseAndThrowContainer() : useAndThrowContainer;
    }

}
