package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
@Entity
@Table(name = "mediums")
public class MediumsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long medium_id;

    private String description;

    private String type;

    private String source;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "medium")
    private Set<TicketsEntity> tickets;

    public Long getMedium_id() {
        return medium_id;
    }

    public void setMedium_id(Long medium_id) {
        this.medium_id = medium_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<TicketsEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketsEntity> tickets) {
        this.tickets = tickets;
    }
}
