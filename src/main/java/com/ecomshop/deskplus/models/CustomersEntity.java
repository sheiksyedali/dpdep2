package com.ecomshop.deskplus.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
@Entity
@Table(name = "csutomers")
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long customer_id;

    private String customer_name;

    private String customer_email;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<TicketsEntity> tickets;

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public Set<TicketsEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketsEntity> tickets) {
        this.tickets = tickets;
    }
}
