package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.TicketMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
public interface TicketMessagesRepository extends JpaRepository<TicketMessagesEntity, Long> {

    @Query("SELECT count(t.ticket_message_id) FROM TicketMessagesEntity t WHERE t.track_id = :trackId")
    Long getMessageCountForTrackId(@Param("trackId") String trackId);
}
