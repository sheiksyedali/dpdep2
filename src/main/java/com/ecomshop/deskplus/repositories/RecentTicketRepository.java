package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.RecentTicketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
public interface RecentTicketRepository extends JpaRepository<RecentTicketsEntity, Long> {
}
