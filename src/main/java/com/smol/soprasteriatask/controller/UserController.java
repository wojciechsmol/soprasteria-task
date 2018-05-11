package com.smol.soprasteriatask.controller;

import com.smol.soprasteriatask.model.DetailsDTO;
import com.smol.soprasteriatask.model.DetailsNewDTO;
import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.model.UserCreatedDTO;
import com.smol.soprasteriatask.service.SkillsService;
import com.smol.soprasteriatask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService mUserService;
    @Autowired
    private SkillsService mSkillsService;

    //-------------------------------------------------------------------------------------------------------
    // Controllers for: /userInfo - displays info about user.
    // NOTE: it displays only info about user whos ID (currentUserID) set in the app (despite which user is logged in)
    // It is due to limitations of the external API
    //----------------------------------------------------------------------------------------------------------

    @GetMapping("/userInfo")
    public String getUserInfoPage(Model model) {
        List<SkillDTO> allSkills = mSkillsService.getAllSkills();
        DetailsNewDTO detailsNewDTO = mUserService.getCurrentUserDetails();

        //***You can use the code below if external API doesn't work:***
        /*List<SkillDTO> allSkills = new ArrayList<>();
        allSkills.add(new SkillDTO(1, "Java"));
        allSkills.add(new SkillDTO(2, "C++"));*/
        //DetailsNewDTO detailsNewDTO = new DetailsNewDTO("Informatyka", "Wojciech", "Smol", "Polsl", 3);

        // determines if the error message and form should be displayed
        boolean apiRespondedCorrectly = (allSkills != null) && (detailsNewDTO != null);

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);

        //passing current user details so that they can be seen before changing
        model.addAttribute("detailsNewDTO", detailsNewDTO);

        return "/user/userInfo";
    }


    @PostMapping("/userInfo")
    public String saveUserInfo(@ModelAttribute DetailsNewDTO detailsNewDTO, Model model) {

        boolean changesSavedCorrectly = mUserService.saveCurrentUserDetails(detailsNewDTO);
        model.addAttribute(changesSavedCorrectly);
        model.addAttribute("apiRespondedCorrectly", true);

        return "/user/userInfo";
    }

    //----------------------------------------------------------------------------------------------------
    // Controller for: /userSkills - displays user skills.
    // NOTE: It it displays only skills about user whos ID (currentUserID) set in the app (despite which user is logged in)
    // It is due to limitations of the external API
    //---------------------------------------------------------------------------------------------------------

    @GetMapping("/userSkills")
    public String getUserSkillsPage(Model model) {

        List<SkillDTO> userSkills = mUserService.getCurrentUserSkills();

        //***You can use code below if API doesn't work***
       /* List<SkillDTO> userSkills = new ArrayList<>();
        userSkills.add(new SkillDTO(1, "C++"));
        userSkills.add(new SkillDTO(2, "Java"));*/

        // determines if the error message should be displayed
        boolean apiRespondedCorrectly = userSkills != null;

        //passing user skills for displaying
        model.addAttribute("userSkills", userSkills);

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);

        return "/user/userSkills";
    }

    //----------------------------------------------------------------------------------------------------
    // Controllers for : /addUserSkill - lets add a skill to the user whos ID (currentUserID) is set in the app
    //------------------------------------------------------------------------------------------------------

    @GetMapping("/addUserSkill")
    public String getAddUserSkillPage(Model model) {

        List<SkillDTO> allSkills = mSkillsService.getAllSkills();

        //***You can use the code below if external API doesn't work:***
        /*List<SkillDTO> allSkills = new ArrayList<>();
        allSkills.add(new SkillDTO(1, "Java"));
        allSkills.add(new SkillDTO(2, "C++"));*/

        // determines if the error message should be displayed
        boolean apiRespondedCorrectly = allSkills != null;

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);
        model.addAttribute("allSkills", allSkills);

        //passing empty SkillDTO object for populating id field
        model.addAttribute("skillDTO", new SkillDTO());

        return "/user/addUserSkill";
    }

    @PostMapping("/addUserSkill")
    public String saveUserSkill(@ModelAttribute SkillDTO skillDTO, Model model) {

        //determines if everything went well
        boolean apiRespondedCorrectly = mUserService.addSkillToCurrentUser(skillDTO.getId());

        model.addAttribute("apiRespondedCorrectly", apiRespondedCorrectly);

        return "/user/addUserSkill";
    }

    //--------------------------------------------------------------------------
    // Controllers for: /findUser
    //---------------------------------------------------------------------------
    @GetMapping("/findUser")
    public String getFindUserPage(Model model) {

        //passing empty DetailsDTOobject for populating the id field
        model.addAttribute("detailsDTO", new DetailsDTO());
        return "findUser";
    }

    @PostMapping("/findUser")
    public String getResultPageFindUser(@ModelAttribute UserCreatedDTO userCreatedDTO, Model model) {

        UserCreatedDTO foundUser = mUserService.findUserById(userCreatedDTO.getId());

        //***You can use code below if API does not respond:***
        //UserCreatedDTO foundUser = new UserCreatedDTO("my_mail@gmail.com", "2543", "Wojtek", "pass");

        //setting variable for determining about displaying alert and user info
        // if foundUser = null -> found = false;
        boolean found = foundUser != null;

        model.addAttribute("found", found);
        model.addAttribute("userCreatedDTO", foundUser);

        return "findUserResults";
    }


}
