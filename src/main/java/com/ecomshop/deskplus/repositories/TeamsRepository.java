package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.TeamsEntity;
import com.ecomshop.deskplus.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 24 Oct 2021
 */
public interface TeamsRepository extends JpaRepository<TeamsEntity, Long> {

    @Query("SELECT t FROM TeamsEntity t " +
            "INNER JOIN RegistrationsEntity r ON r.reg_id = t.registration.reg_id " +
            "WHERE t.team_name = :teamName AND r.reg_unique_id = :regId")
    TeamsEntity findByTeamName(@Param("teamName") String teamName, @Param("regId") String regId);

}
