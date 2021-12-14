package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.TeamMembersEntity;
import com.ecomshop.deskplus.models.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 02 Nov 2021
 */
public interface TeamMembersRepository extends JpaRepository<TeamMembersEntity, Long> {

    @Query("DELETE FROM TeamMembersEntity t WHERE t.team.team_id = :teamId")
    void deleteTeamMembers(@Param("teamId") Long teamId);

}
