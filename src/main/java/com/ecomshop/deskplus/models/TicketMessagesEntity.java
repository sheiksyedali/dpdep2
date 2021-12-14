package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
@Entity
@Table(name = "ticket_messages")
public class TicketMessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long ticket_message_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketsEntity ticket;

    private String track_id;

    @Column(columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_by", nullable = false)
    private UsersEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reply_time;

    private Integer thread_id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "ticketMessage")
    private Set<RecentTicketsEntity> recentTickets;

    public Long getTicket_message_id() {
        return ticket_message_id;
    }

    public void setTicket_message_id(Long ticket_message_id) {
        this.ticket_message_id = ticket_message_id;
    }

    public TicketsEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketsEntity ticket) {
        this.ticket = ticket;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public Date getReply_time() {
        return reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }

    public Integer getThread_id() {
        return thread_id;
    }

    public void setThread_id(Integer thread_id) {
        this.thread_id = thread_id;
    }

    public Set<RecentTicketsEntity> getRecentTickets() {
        return recentTickets;
    }

    public void setRecentTickets(Set<RecentTicketsEntity> recentTickets) {
        this.recentTickets = recentTickets;
    }
}
