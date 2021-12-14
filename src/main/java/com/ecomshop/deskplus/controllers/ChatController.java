package com.ecomshop.deskplus.controllers;

import com.ecomshop.deskplus.services.ChatServices;
import com.ecomshop.deskplus.services.chat.ChatMessage;
import com.ecomshop.deskplus.web.rest.ChatRequest;
import com.ecomshop.deskplus.web.rest.ReplyRequest;
import com.ecomshop.deskplus.web.rest.Response;
import com.ecomshop.deskplus.web.rest.UpdateChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatServices chatServices;

    @RequestMapping(value = "/live/customer/send", method = RequestMethod.POST)
    public Response messageFromCustomer(@RequestBody ChatRequest chatRequest) {
        return chatServices.messageFromCustomer(chatRequest);
    }

    /**
     * Planning for 3 sec polling or think alternate communication
     */
    @GetMapping("/live/user/new/{regUid}")
    public List<ChatMessage> getNewMessage(@PathVariable String regUid) {
        return chatServices.getNewMessages(regUid);
    }

    @GetMapping("/live/user/get/{regUid}/{trackId}/{userId}")
    public Response getMessageForUser(@PathVariable String regUid, @PathVariable String trackId, @PathVariable Long userId){
        return chatServices.getMessageForUser(regUid, trackId, userId);
    }

    @RequestMapping(value = "/live/user/reply", method = RequestMethod.POST)
    public Response replyFromUser(@RequestBody ReplyRequest replyRequest){
        return chatServices.messageFromUser(replyRequest);
    }

    @GetMapping("/live/customer/get/{regUid}/{trackId}")
    public Response getMessageForCustomer(@PathVariable String regUid, @PathVariable String trackId){
        return chatServices.getMessageForCustomer(regUid, trackId);
    }

    @RequestMapping(value = "/live/user/status", method = RequestMethod.POST)
    public Response updateChatStatus(@RequestBody UpdateChatRequest updateChatRequest) {
        return chatServices.updateChatStatus(updateChatRequest);
    }
}
