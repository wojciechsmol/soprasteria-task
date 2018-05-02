package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveSkillsRequest {

    private List<Integer> skillIds;
    private String userId;

    public SaveSkillsRequest(List<Integer> skillIds, String userId) {
        this.skillIds = skillIds;
        this.userId = userId;
    }

    public SaveSkillsRequest() {
    }

    public List<Integer> getSkillIds() {
        return skillIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setSkillIds(List<Integer> skillIds) {
        this.skillIds = skillIds;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SaveSkillsRequest{" +
                "skillIds=" + skillIds +
                ", userId='" + userId + '\'' +
                '}';
    }
}
