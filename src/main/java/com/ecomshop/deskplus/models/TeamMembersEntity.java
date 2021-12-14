package com.ecomshop.deskplus.models;

import javax.persistence.*;

/**
 * Author: Sheik Syed Ali
 * Date: 02 Nov 2021
 */
@Entity
@Table(name = "team_members")
public class TeamMembersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long team_member_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamsEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member", nullable = false)
    private UsersEntity user;

    public Long getTeam_member_id() {
        return team_member_id;
    }

    public void setTeam_member_id(Long team_member_id) {
        this.team_member_id = team_member_id;
    }

    public TeamsEntity getTeam() {
        return team;
    }

    public void setTeam(TeamsEntity team) {
        this.team = team;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
