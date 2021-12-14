package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 24 Oct 2021
 */
@Entity
@Table(name = "teams")
public class TeamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long team_id;

    private String team_name;

    private String team_description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date team_creation_time;

    @Temporal(TemporalType.TIMESTAMP)
    private Date team_last_updated_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UsersEntity createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private UsersEntity updatedUser;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "team")
    private Set<TeamMembersEntity> teamMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", nullable = false)
    private RegistrationsEntity registration;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "team")
    private Set<TicketsEntity> tickets;

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_description() {
        return team_description;
    }

    public void setTeam_description(String team_description) {
        this.team_description = team_description;
    }

    public Date getTeam_creation_time() {
        return team_creation_time;
    }

    public void setTeam_creation_time(Date team_creation_time) {
        this.team_creation_time = team_creation_time;
    }

    public Date getTeam_last_updated_time() {
        return team_last_updated_time;
    }

    public void setTeam_last_updated_time(Date team_last_updated_time) {
        this.team_last_updated_time = team_last_updated_time;
    }

    public UsersEntity getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(UsersEntity createdUser) {
        this.createdUser = createdUser;
    }

    public UsersEntity getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(UsersEntity updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Set<TeamMembersEntity> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMembersEntity> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public RegistrationsEntity getRegistration() {
        return registration;
    }

    public void setRegistration(RegistrationsEntity registration) {
        this.registration = registration;
    }

    public Set<TicketsEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketsEntity> tickets) {
        this.tickets = tickets;
    }
}
