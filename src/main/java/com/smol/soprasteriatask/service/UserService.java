package com.smol.soprasteriatask.service;

import com.smol.soprasteriatask.model.DetailsDTO;
import com.smol.soprasteriatask.model.DetailsNewDTO;
import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.model.UserCreatedDTO;

import java.util.List;

public interface UserService {

    UserCreatedDTO findUserById(String id);
    List<SkillDTO> getCurrentUserSkills();
    void addSkillToCurrentUser(int skillId);
    DetailsNewDTO getCurrentUserDetails();
    void saveCurrentUserDetails(DetailsNewDTO details);
}
