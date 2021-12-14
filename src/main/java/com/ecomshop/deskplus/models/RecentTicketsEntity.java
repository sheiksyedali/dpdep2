package com.ecomshop.deskplus.models;

import javax.persistence.*;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
@Entity
@Table(name = "recent_tickets")
public class RecentTicketsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long recent_ticket_id;

    private String track_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketsEntity ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_message_id", nullable = false)
    private TicketMessagesEntity ticketMessage;

    public Long getRecent_ticket_id() {
        return recent_ticket_id;
    }

    public void setRecent_ticket_id(Long recent_ticket_id) {
        this.recent_ticket_id = recent_ticket_id;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public TicketsEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketsEntity ticket) {
        this.ticket = ticket;
    }

    public TicketMessagesEntity getTicketMessage() {
        return ticketMessage;
    }

    public void setTicketMessage(TicketMessagesEntity ticketMessage) {
        this.ticketMessage = ticketMessage;
    }
}
