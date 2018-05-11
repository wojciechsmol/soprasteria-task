package com.smol.soprasteriatask.controller;

import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.model.SkillNewDTO;
import com.smol.soprasteriatask.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SkillsController {

    @Autowired
    private SkillsService mSkillsService;

    //----------------------------------------------------------------------------------
    // Controller for: /skills
    //----------------------------------------------------------------------------------
    @GetMapping("/skills")
    public String getSkillsPage(Model model) {

       List<SkillDTO> allSkills = mSkillsService.getAllSkills();

        //***You can use code from below if external API does not respond:***
       /* List<SkillDTO> allSkills = new ArrayList<>();
        allSkills.addAll(Arrays.asList(new SkillDTO(1, "Java"),
                new SkillDTO(2, "C++"),
                new SkillDTO(3, "SQL"),
                new SkillDTO(4, "HTML"),
                new SkillDTO(5, "Spring")));*/

        // determines if the error message should be displayed
        boolean apiRespondedCorrectly = allSkills != null;

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);
        model.addAttribute("allSkills", allSkills);

        return "skills";
    }

    //----------------------------------------------------------------------------------
    // Controllers for: /addNewSkill
    //----------------------------------------------------------------------------------
    @GetMapping("/addNewSkill")
    public String getAddNewSkillPage(Model model) {

        //passing empty SkillNewDTO to populate id field
        model.addAttribute("skillNewDTO", new SkillNewDTO());
        return "addNewSkill";
    }

    @PostMapping("/addNewSkill")
    public String saveNewSkill(@ModelAttribute SkillNewDTO skillNewDTO, Model model) {

        //determines if everything wen well
        boolean apiRespondedCorrectly = mSkillsService.addNewSkill(skillNewDTO);

        //***You can use code below if external API does not repond:***
        //boolean apiRespondedCorrectly = true;

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);

        return "addNewSkill";
    }

}
