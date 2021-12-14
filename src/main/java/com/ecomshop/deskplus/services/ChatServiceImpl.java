package com.ecomshop.deskplus.services;

import com.ecomshop.deskplus.models.*;
import com.ecomshop.deskplus.repositories.*;
import com.ecomshop.deskplus.services.chat.ChatMessage;
import com.ecomshop.deskplus.services.chat.MessageBin;
import com.ecomshop.deskplus.services.chat.ReplyMessage;
import com.ecomshop.deskplus.web.rest.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Sheik Syed Ali
 * Date: 13 Nov 2021
 */
@Service
public class ChatServiceImpl implements ChatServices {

    @Autowired
    private MediumsRepository mediumsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TicketMessagesRepository ticketMessagesRepository;

    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Autowired
    protected UsersRepository usersRepository;

    @Override
    public Response messageFromCustomer(ChatRequest chatRequest) {
        String sourceId = chatRequest.getSourceId();
        if(sourceId != null && sourceId.isEmpty()){
            boolean isNewMessage = false;
            MediumsEntity mediumsEntity = mediumsRepository.findMediumBySource(sourceId);
            if(mediumsEntity == null){
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_MEDIUM_NOT_FOUND, ResponseConstants.ERR_CHAT_MEDIUM_NOT_FOUND_MSG);
            }
            String trackId = chatRequest.getTrackId();
            if(trackId == null){
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND, ResponseConstants.ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND_MSG);
            }

            String regUid = chatRequest.getRegUid();
            RegistrationsEntity registrationsEntity = registrationsRepository.getRegistrationByRegUniqueId(regUid);
            if(registrationsEntity == null){
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_REGISTRATION_NOT_FOUND, ResponseConstants.ERR_REGISTRATION_NOT_FOUND_MSG);
            }

            String customerEmail = chatRequest.getEmail();
            CustomersEntity customerEntity = customersRepository.findCustomerByEmail(customerEmail);
            if(customerEntity == null){
                customerEntity = new CustomersEntity();
                customerEntity.setCustomer_name(chatRequest.getCustomerName());
                customerEntity.setCustomer_email(customerEmail);
                customerEntity = customersRepository.saveAndFlush(customerEntity);
            }

            TicketsEntity ticketEntity = ticketsRepository.getTicketByTrackId(trackId, regUid);
            if(ticketEntity == null){
                ticketEntity = new TicketsEntity();
                ticketEntity.setCustomer(customerEntity);
                ticketEntity.setMedium(mediumsEntity);
                ticketEntity.setTrack_id(trackId);
                Date currentTime = new Date();
                ticketEntity.setCreated_time(currentTime);
                ticketEntity.setStatus(TicketConstants.TICKET_STATUS_OPEN);
                ticketEntity.setLast_updated_time(currentTime);
                ticketEntity.setRegistration(registrationsEntity);
                ticketEntity = ticketsRepository.saveAndFlush(ticketEntity);
                isNewMessage = true;
            }

            Long messageCount = ticketMessagesRepository.getMessageCountForTrackId(trackId);
            if(messageCount == null){
                messageCount = 1l;
            } else {
                messageCount = messageCount + 1;
            }
            TicketMessagesEntity ticketMessageEntity = new TicketMessagesEntity();
            ticketMessageEntity.setTicket(ticketEntity);
            ticketMessageEntity.setTrack_id(trackId);
            ticketMessageEntity.setMessage(chatRequest.getMessage());
            ticketMessageEntity.setThread_id(messageCount.intValue());
            ticketMessageEntity = ticketMessagesRepository.saveAndFlush(ticketMessageEntity);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setRegUid(regUid);
            chatMessage.setCustomerName(chatRequest.getCustomerName());
            chatMessage.setCustomerEmail(customerEmail);
            chatRequest.setTrackId(trackId);
            chatMessage.setRawMessage(chatRequest.getMessage());
            chatMessage.setSourceId(chatRequest.getSourceId());
            chatMessage.setTime(new Date());
            chatMessage.setNew(isNewMessage);
            MessageBin.getInstance().addChatToCache(regUid, trackId, chatMessage);

            return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.CUSTOMER_CHAT_MESSAGE_ADDED);
        } else {
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_SOURCE_ID_NOT_FOUND, ResponseConstants.ERR_CHAT_SOURCE_ID_NOT_FOUND_MSG);
        }
    }

    @Override
    public List<ChatMessage> getNewMessages(String regUid) {
        return MessageBin.getInstance().getNewMessages(regUid);
    }

    @Override
    public Response getMessageForUser(String regUid, String trackId, Long userId) {
        UsersEntity user = usersRepository.getUser(userId, regUid);
        if(user == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_USER, ResponseConstants.ERR_INVALID_USER_MSG);
        }

        RegistrationsEntity registrationsEntity = registrationsRepository.getRegistrationByRegUniqueId(regUid);
        if(registrationsEntity == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_REGISTRATION_NOT_FOUND, ResponseConstants.ERR_REGISTRATION_NOT_FOUND_MSG);
        }

        Map<String, Object> response = new HashMap<>();
        ChatMessage chatMessage = MessageBin.getInstance().getChatMessage(regUid, trackId);
        if(chatMessage != null){
            response.put("message", chatMessage);
        }
        return ResponseManager.getInstance().createSuccessDetailedResponse(response);
    }

    @Override
    public Response messageFromUser(ReplyRequest replyRequest) {
        Long userId = replyRequest.getUserId();
        String sourceId = replyRequest.getSourceId();
        String regUid = replyRequest.getRegUid();

        if(sourceId != null && sourceId.isEmpty()) {
            UsersEntity user = usersRepository.getUser(userId, regUid);
            if(user == null){
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_USER, ResponseConstants.ERR_INVALID_USER_MSG);
            }

            MediumsEntity mediumsEntity = mediumsRepository.findMediumBySource(sourceId);
            if (mediumsEntity == null) {
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_MEDIUM_NOT_FOUND, ResponseConstants.ERR_CHAT_MEDIUM_NOT_FOUND_MSG);
            }
            String trackId = replyRequest.getTrackId();
            if (trackId == null) {
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND, ResponseConstants.ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND_MSG);
            }

            RegistrationsEntity registrationsEntity = registrationsRepository.getRegistrationByRegUniqueId(regUid);
            if (registrationsEntity == null) {
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_REGISTRATION_NOT_FOUND, ResponseConstants.ERR_REGISTRATION_NOT_FOUND_MSG);
            }

            TicketsEntity ticketEntity = ticketsRepository.getTicketByTrackId(trackId, regUid);
            if(ticketEntity == null){
                return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_MESSAGE_TRACK_ID, ResponseConstants.ERR_INVALID_MESSAGE_TRACK_ID_MSG);
            }

            Long messageCount = ticketMessagesRepository.getMessageCountForTrackId(trackId);
            if(messageCount == null){
                messageCount = 1l;
            } else {
                messageCount = messageCount + 1;
            }

            TicketMessagesEntity ticketMessageEntity = new TicketMessagesEntity();
            ticketMessageEntity.setTicket(ticketEntity);
            ticketMessageEntity.setTrack_id(trackId);
            ticketMessageEntity.setMessage(replyRequest.getMessage());
            ticketMessageEntity.setUser(user);
            ticketMessageEntity.setReply_time(new Date());
            ticketMessageEntity.setThread_id(messageCount.intValue());
            ticketMessageEntity = ticketMessagesRepository.saveAndFlush(ticketMessageEntity);

            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setRegId(regUid);
            replyMessage.setUserId(userId);
            replyMessage.setUserName(user.getFirst_name()+user.getLast_name());
            replyMessage.setRawMessage(replyRequest.getMessage());
            replyMessage.setTrackId(trackId);
            replyMessage.setTime(new Date());
            replyMessage.setSourceId(replyRequest.getSourceId());
            MessageBin.getInstance().addReplyToCache(regUid, trackId, replyMessage);

            return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.USER_CHAT_REPLY_ADDED);
        } else {
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_CHAT_SOURCE_ID_NOT_FOUND, ResponseConstants.ERR_CHAT_SOURCE_ID_NOT_FOUND_MSG);
        }
    }

    @Override
    public Response getMessageForCustomer(String regUid, String trackId) {
        RegistrationsEntity registrationsEntity = registrationsRepository.getRegistrationByRegUniqueId(regUid);
        if(registrationsEntity == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_REGISTRATION_NOT_FOUND, ResponseConstants.ERR_REGISTRATION_NOT_FOUND_MSG);
        }

        Map<String, Object> response = new HashMap<>();
        ReplyMessage replyMessage = MessageBin.getInstance().getReplyMessage(regUid, trackId);
        if(replyMessage != null){
            response.put("message", replyMessage);
        }
        return ResponseManager.getInstance().createSuccessDetailedResponse(response);
    }

    @Override
    public Response updateChatStatus(UpdateChatRequest updateChatRequest) {
        Long userId = updateChatRequest.getUserId();
        String regUid = updateChatRequest.getRegUid();

        UsersEntity user = usersRepository.getUser(userId, regUid);
        if(user == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_USER, ResponseConstants.ERR_INVALID_USER_MSG);
        }

        String trackId = updateChatRequest.getTrackId();
        String status = updateChatRequest.getStatus();

        TicketsEntity ticketEntity = ticketsRepository.getTicketByTrackId(trackId, regUid);
        if(ticketEntity == null){
            return ResponseManager.getInstance().createFailureResponse(ResponseConstants.ERR_INVALID_MESSAGE_TRACK_ID, ResponseConstants.ERR_INVALID_MESSAGE_TRACK_ID_MSG);
        }
        ticketEntity.setStatus(status);
        ticketEntity.setUser(user);
        ticketEntity.setLast_updated_time(new Date());
        ticketsRepository.saveAndFlush(ticketEntity);

        return ResponseManager.getInstance().createSuccessResponse(ResponseConstants.CHAT_STATUS_UPDATE_SUCCEEDED);
    }
}
