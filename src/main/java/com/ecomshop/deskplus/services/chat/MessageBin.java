package com.ecomshop.deskplus.services.chat;

import com.ecomshop.deskplus.services.utils.RandomUtil;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public class MessageBin {

    private Map<String, Map<String, ChatSession>> chatSessions;
    private Map<String, Map<String, ChatMessage>> customerMessage;
    private Map<String, Map<String, ReplyMessage>> userReplies;

    private final ReentrantLock reLock = new ReentrantLock(true);

    private static MessageBin messageBin;

    private MessageBin(){
        customerMessage = new ConcurrentHashMap<>();
        userReplies = new ConcurrentHashMap<>();
        chatSessions = new ConcurrentHashMap<>();
    }

    public void incoming(JSONObject payload, WebSocketSession session) {
        /*** JSON payload
         *  regId: registration_id (mandatory)
         *  trackId: unique_message_track_id (if id is null or empty then message consider as new message from customer)
         *  type: customer_message | user_reply (mandatory)
         ****/
        reLock.lock();
        try{
            String type = payload.getString("type");
            String trackId = (String) payload.get("trackId");
            String regId = payload.getString("regId");

            if(type != null && !type.isEmpty()){
                if(type.equals(ChatConstants.CHAT_CUSTOMER)){
                    if(trackId == null){
                        trackId = RandomUtil.generateRandomKey();
                    }
                    if(chatSessions.containsKey(regId)) {
                        Map<String, ChatSession> sessions = chatSessions.get(regId);
                        if(sessions.containsKey(trackId)){
                            //Existing message from customer
                            ChatSession chatSession = sessions.get(trackId);
                            chatSession.setCustomerSession(session);
                            sessions.put(trackId, chatSession);
                            chatSessions.put(regId, sessions);

                            //Send message to user
                            WebSocketSession userSession = chatSession.getUserSession();
                            if(userSession != null){
                                JSONObject responsePayload = new JSONObject();
                                responsePayload.put("regId", regId);
                                responsePayload.put("trackId", trackId);
                                sendTextMessage(responsePayload.toString(), userSession);
                            }
                        } else {
                            // New message from customer
                            ChatSession chatSession = new ChatSession();
                            chatSession.setCustomerSession(session);
                            sessions.put(trackId, chatSession);
                            chatSessions.put(regId, sessions);

                            //Send to customer
                            JSONObject responsePayload = new JSONObject();
                            responsePayload.put("regId", regId);
                            responsePayload.put("trackId", trackId);
                            sendTextMessage(responsePayload.toString(), session);
                        }
                    } else {
                        // New message from customer
                        Map<String, ChatSession> sessions = new HashMap<>();
                        ChatSession chatSession = new ChatSession();
                        chatSession.setCustomerSession(session);
                        sessions.put(trackId, chatSession);
                        chatSessions.put(regId, sessions);

                        //Send to customer
                        JSONObject responsePayload = new JSONObject();
                        responsePayload.put("regId", regId);
                        responsePayload.put("trackId", trackId);
                        sendTextMessage(responsePayload.toString(), session);
                    }
                } else if(type.equals(ChatConstants.CHAT_USER)) {
                    Map<String, ChatSession> sessions = chatSessions.get(regId);
                    ChatSession chatSession = sessions.get(trackId);
                    chatSession.setUserSession(session);
                    sessions.put(trackId, chatSession);
                    chatSessions.put(regId, sessions);

                    //Send message to customer
                    WebSocketSession customerSession = chatSession.getCustomerSession();
                    JSONObject responsePayload = new JSONObject();
                    responsePayload.put("regId", regId);
                    responsePayload.put("trackId", trackId);
                    sendTextMessage(responsePayload.toString(), customerSession);
                }
            }
        } finally {
            reLock.unlock();
        }
    }

    public void close(String regId, String trackId){
        reLock.lock();
        try {
            Map<String, ChatSession> sessions = chatSessions.get(regId);
            ChatSession chatSession = sessions.get(trackId);
            WebSocketSession customerSession = chatSession.getCustomerSession();
            if(customerSession != null){
                customerSession.close();
            }

            WebSocketSession userSession = chatSession.getUserSession();
            if(userSession != null){
                userSession.close();
            }

            sessions.remove(trackId);
            chatSessions.put(regId, sessions);
        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
            reLock.unlock();
        }
    }

    public void addChatToCache(String regId, String trackId, ChatMessage chatMessage){
        reLock.lock();
        try {
            if(customerMessage.containsKey(regId)){
                Map<String, ChatMessage> messages = customerMessage.get(regId);
                if(messages.containsKey(trackId)){
                    ChatMessage existingChatMessage = messages.get(trackId);
                    Map<String, String> messageThread = existingChatMessage.getMessageThread();
                    int idx = messageThread.size()+1;
                    messageThread.put(""+idx, chatMessage.getRawMessage());
                    chatMessage.setMessageThread(messageThread);
                    messages.put(trackId, chatMessage);
                    customerMessage.put(regId, messages);
                } else{
                    Map<String, String> messageThread = new HashMap<>();
                    messageThread.put("1", chatMessage.getRawMessage());
                    chatMessage.setMessageThread(messageThread);
                    messages.put(trackId, chatMessage);
                    customerMessage.put(regId, messages);
                }
            } else{
                Map<String, ChatMessage> messages = new HashMap<>();
                Map<String, String> messageThread = new HashMap<>();
                messageThread.put("1", chatMessage.getRawMessage());
                chatMessage.setMessageThread(messageThread);
                messages.put(trackId, chatMessage);
                customerMessage.put(regId, messages);
            }
        } finally {
            reLock.unlock();
        }
    }

    public void addReplyToCache(String regId, String trackId, ReplyMessage replyMessage){
        reLock.lock();
        try{
            if(userReplies.containsKey(regId)){
                Map<String, ReplyMessage> replies = userReplies.get(regId);
                if(replies.containsKey(trackId)){
                    ReplyMessage existingReplyMessage = replies.get(trackId);
                    Map<String, String> replyThread = existingReplyMessage.getMessageThread();
                    int idx = replyThread.size()+1;
                    replyThread.put(""+idx, replyMessage.getRawMessage());
                    replyMessage.setMessageThread(replyThread);
                    replies.put(trackId, replyMessage);
                    userReplies.put(regId, replies);
                }else{
                    Map<String, String> replyThread = new HashMap<>();
                    replyThread.put("1", replyMessage.getRawMessage());
                    replyMessage.setMessageThread(replyThread);
                    replies.put(trackId, replyMessage);
                    userReplies.put(regId, replies);
                }
            } else {
                Map<String, ReplyMessage> replies = new HashMap<>();
                Map<String, String> replyThread = new HashMap<>();
                replyThread.put("1", replyMessage.getRawMessage());
                replyMessage.setMessageThread(replyThread);
                replies.put(trackId, replyMessage);
                userReplies.put(regId, replies);
            }
        }finally {
            reLock.unlock();
        }
    }

    public List<ChatMessage> getNewMessages(String regUid){
        List<ChatMessage> messages = new ArrayList<>();
        if(customerMessage.containsKey(regUid)){
            Map<String, ChatMessage> customerMessages = customerMessage.get(regUid);
            for(Map.Entry<String, ChatMessage> customerMessage : customerMessages.entrySet()){
                ChatMessage chatMessage = customerMessage.getValue();
                if(chatMessage.isNew()){
                    messages.add(chatMessage);
                }
            }
        }
        return messages;
    }

    public ChatMessage getChatMessage(String regUid, String trackId){
        ChatMessage chatMessage = null;
        if(customerMessage.containsKey(regUid)){
            Map<String, ChatMessage> messages = customerMessage.get(regUid);
            if(messages.containsKey(trackId)){
                chatMessage = messages.get(trackId);
            }
        }
        return chatMessage;
    }

    public ReplyMessage getReplyMessage(String regUid, String trackId){
        ReplyMessage replyMessage = null;
        if(userReplies.containsKey(regUid)){
            Map<String, ReplyMessage> replies = userReplies.get(regUid);
            if(replies.containsKey(trackId)){
                replyMessage = replies.get(trackId);
            }
        }
        return replyMessage;
    }

    private void sendTextMessage(String payload, WebSocketSession session){
        try{
            session.sendMessage(new TextMessage(payload));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static MessageBin getInstance(){
        return (messageBin == null) ? messageBin = new MessageBin() : messageBin;
    }

}
