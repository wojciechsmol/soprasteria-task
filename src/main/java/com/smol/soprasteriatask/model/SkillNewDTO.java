package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillNewDTO {

    private String skillName;

    public SkillNewDTO(String skillName) {
        this.skillName = skillName;
    }

    public SkillNewDTO() {
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "SkillNewDTO{" +
                "skillName='" + skillName + '\'' +
                '}';
    }
}
