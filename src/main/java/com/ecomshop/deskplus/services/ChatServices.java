package com.ecomshop.deskplus.services;

import com.ecomshop.deskplus.services.chat.ChatMessage;
import com.ecomshop.deskplus.web.rest.ChatRequest;
import com.ecomshop.deskplus.web.rest.ReplyRequest;
import com.ecomshop.deskplus.web.rest.Response;
import com.ecomshop.deskplus.web.rest.UpdateChatRequest;

import java.util.List;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
public interface ChatServices {
    Response messageFromCustomer(ChatRequest chatRequest);

    List<ChatMessage> getNewMessages(String regUid);

    Response getMessageForUser(String regUid, String trackId, Long userId);

    Response messageFromUser(ReplyRequest replyRequest);

    Response getMessageForCustomer(String regUid, String trackId);

    Response updateChatStatus(UpdateChatRequest updateChatRequest);
}

