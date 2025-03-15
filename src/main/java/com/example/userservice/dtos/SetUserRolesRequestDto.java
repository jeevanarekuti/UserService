package com.example.userservice.dtos;

import lombok.Getter;

import java.util.List;

@Getter
public class SetUserRolesRequestDto {
    private List<Long> userRoleIds;

    public List<Long> getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(List<Long> userRoleIds) {
        this.userRoleIds = userRoleIds;
    }
}
