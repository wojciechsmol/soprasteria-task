package com.smol.soprasteriatask.service;

import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.model.SkillNewDTO;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface SkillsService {

    List<SkillDTO> getAllSkills();
    void addNewSkill(SkillNewDTO skillNewDTO);
}
