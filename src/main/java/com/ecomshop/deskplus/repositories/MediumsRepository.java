package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.MediumsEntity;
import com.ecomshop.deskplus.models.PlansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
public interface MediumsRepository extends JpaRepository<MediumsEntity, Long> {

    @Query("SELECT m FROM MediumsEntity m WHERE m.source = :source")
    MediumsEntity findMediumBySource(@Param("source") String source);
}
