package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.TicketsEntity;
import com.ecomshop.deskplus.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
public interface TicketsRepository extends JpaRepository<TicketsEntity, Long> {

    @Query("SELECT t FROM TicketsEntity t " +
            "INNER JOIN RegistrationsEntity r ON r.reg_id = t.registration.reg_id " +
            "WHERE t.track_id = :trackId AND r.reg_unique_id = :regId")
    TicketsEntity getTicketByTrackId(@Param("trackId") String trackId, @Param("regId") String regId);
}
