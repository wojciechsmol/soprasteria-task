package com.smol.soprasteriatask.controller;

import com.smol.soprasteriatask.model.DetailsDTO;
import com.smol.soprasteriatask.model.DetailsNewDTO;
import com.smol.soprasteriatask.model.SaveSkillsRequest;
import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.service.SkillsService;
import com.smol.soprasteriatask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {


    private UserService mUserService;
    private SkillsService mSkillsService;

    @Autowired
    public UserController(UserService userService, SkillsService skillsService) {
        mUserService = userService;
        mSkillsService = skillsService;
    }

    //creating useful allSkills list attribute
    @ModelAttribute("allSkills")
    public List<SkillDTO> getAllSkills() {
        return mSkillsService.getAllSkills();
    }

    @GetMapping("/userInfo")
    public String getUserInfoPage(Model model) {

        //passing current user details so that they can be seen before changing
        model.addAttribute("detailsNewDTO", mUserService.getCurrentUserDetails());

        return "/user/userInfo";
    }


    @PostMapping("/userInfo")
    public String saveUserInfo(@ModelAttribute DetailsNewDTO detailsNewDTO) {

        mUserService.saveCurrentUserDetails(detailsNewDTO);
        return "/user/userInfo";
    }

    @GetMapping("/userSkills")
    public String getUserSkillsPage(Model model) {

        //passing user skills for displaying
        model.addAttribute("userSkills", mUserService.getCurrentUserSkills());

        return "/user/userSkills";
    }

    @GetMapping("/addUserSkill")
    public String getAddUserSkillPage(Model model) {

        //passing empty SkillDTO object for populating id field
        model.addAttribute("skillDTO", new SkillDTO());

        return "/user/addUserSkill";
    }

    @PostMapping("/addUserSkill")
    public String saveUserSkill(@ModelAttribute SkillDTO skillDTO) {

        mUserService.addSkillToCurrentUser(skillDTO.getId());
        return "/user/addUserSkill";
    }



}
