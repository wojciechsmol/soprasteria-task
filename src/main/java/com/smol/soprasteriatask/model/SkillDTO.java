package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDTO {

    private int id;
    private String skillName;

    public SkillDTO(int id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }

    public SkillDTO() {
    }

    public int getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
