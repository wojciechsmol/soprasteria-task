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

import java.util.List;

@Controller
public class SkillsController {

    @Autowired
    private SkillsService mSkillsService;

    @Autowired
    public SkillsController(SkillsService skillsService) {
        mSkillsService = skillsService;
    }

    //creating useful allSkills list attribute
    @ModelAttribute("allSkills")
    public List<SkillDTO> getAllSkills() {

        //temporary if API not working:
        /*List<SkillDTO> skills = new ArrayList<>();
        skills.add(new SkillDTO(1, "Jakas"));
        skills.add(new SkillDTO(2, "Jakas2"));
        return skills;*/

        return mSkillsService.getAllSkills();
    }

    @GetMapping("/skills")
    public String getSkillsPage() {
        return "skills";
    }


    @GetMapping("/addNewSkill")
    public String getAddNewSkillPage(Model model) {

        //passing empty SkillNewDTO to populate id field
        model.addAttribute("skillNewDTO", new SkillNewDTO());
        return "addNewSkill";
    }

    @PostMapping("/addNewSkill")
    public String saveNewSkill(@ModelAttribute SkillNewDTO skillNewDTO) {

        mSkillsService.addNewSkill(skillNewDTO);
        return "addNewSkill";
    }

}
