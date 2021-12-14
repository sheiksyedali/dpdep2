package com.ecomshop.deskplus.web.rest;

/**
 * Author: Sheik Syed Ali
 * Date: 02 Nov 2021
 */
public class DeleteTeamRequest {

    private Long teamId;

    private Long deletedBy;

    String regId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
