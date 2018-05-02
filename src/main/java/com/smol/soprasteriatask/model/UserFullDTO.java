package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFullDTO {

    private String email;
    private String id;
    private String name;
    private String password;
    private List<SkillDTO> skills;

    public UserFullDTO(String email, String id, String name, String password, List<SkillDTO> skills) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.skills = skills;
    }

    public UserFullDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<SkillDTO> getSkills() {
        return skills;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSkills(List<SkillDTO> skills) {
        this.skills = skills;
    }
}
