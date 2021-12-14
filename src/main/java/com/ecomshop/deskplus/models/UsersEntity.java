package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 24 Sep 2021
 */
@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long user_id;

    private String first_name;

    private String last_name;

    private String email;

    private String password;

    private String bio;

    private String time_zone;

    private String language;

    private boolean is_account_owner;

    private Long created_by;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", nullable = false)
    private RegistrationsEntity registration;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_privileges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RolesEntity> roles;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "createdUser")
    private Set<TeamsEntity> teams;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "updatedUser")
    private Set<TeamsEntity> teams1;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
    private Set<TeamMembersEntity> teamMembers;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
    private Set<TicketsEntity> tickets;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
    private Set<TicketMessagesEntity> ticketMessages;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public RegistrationsEntity getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationsEntity registration) {
        this.registration = registration;
    }

    public List<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesEntity> roles) {
        this.roles = roles;
    }

    public boolean isIs_account_owner() {
        return is_account_owner;
    }

    public void setIs_account_owner(boolean is_account_owner) {
        this.is_account_owner = is_account_owner;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Set<TeamsEntity> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamsEntity> teams) {
        this.teams = teams;
    }

    public Set<TeamsEntity> getTeams1() {
        return teams1;
    }

    public void setTeams1(Set<TeamsEntity> teams1) {
        this.teams1 = teams1;
    }

    public Set<TeamMembersEntity> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMembersEntity> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Set<TicketsEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketsEntity> tickets) {
        this.tickets = tickets;
    }

    public Set<TicketMessagesEntity> getTicketMessages() {
        return ticketMessages;
    }

    public void setTicketMessages(Set<TicketMessagesEntity> ticketMessages) {
        this.ticketMessages = ticketMessages;
    }
}
