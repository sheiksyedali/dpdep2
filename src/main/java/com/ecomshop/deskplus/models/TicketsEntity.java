package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */

@Entity
@Table(name = "tickets")
public class TicketsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long ticket_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomersEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medium_id", nullable = false)
    private MediumsEntity medium;

    private String track_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_time;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_updated_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_user", nullable = false)
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_team", nullable = false)
    private TeamsEntity team;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "ticket")
    private Set<TicketMessagesEntity> ticketMessages;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "ticket")
    private Set<RecentTicketsEntity> recentTickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", nullable = false)
    private RegistrationsEntity registration;

    public Long getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Long ticket_id) {
        this.ticket_id = ticket_id;
    }

    public CustomersEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersEntity customer) {
        this.customer = customer;
    }

    public MediumsEntity getMedium() {
        return medium;
    }

    public void setMedium(MediumsEntity medium) {
        this.medium = medium;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(Date last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }

    public Set<TicketMessagesEntity> getTicketMessages() {
        return ticketMessages;
    }

    public void setTicketMessages(Set<TicketMessagesEntity> ticketMessages) {
        this.ticketMessages = ticketMessages;
    }

    public Set<RecentTicketsEntity> getRecentTickets() {
        return recentTickets;
    }

    public void setRecentTickets(Set<RecentTicketsEntity> recentTickets) {
        this.recentTickets = recentTickets;
    }

    public RegistrationsEntity getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationsEntity registration) {
        this.registration = registration;
    }
}
