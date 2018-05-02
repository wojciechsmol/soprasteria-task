package com.smol.soprasteriatask.controller;

import com.smol.soprasteriatask.model.DetailsDTO;
import com.smol.soprasteriatask.model.UserCreatedDTO;
import com.smol.soprasteriatask.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserServiceImpl mUserService;

    /*@Autowired
    public HomeController(UserServiceImpl userService) {
        mUserService = userService;
    }*/

    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }


    @GetMapping("/findUser")
    public String getFindUserPage(Model model) {

        //passing empty DetailsDTOobject for populating the id field
        model.addAttribute("detailsDTO", new DetailsDTO());
        return "findUser";
    }

    @PostMapping("/findUser")
    public String getResultPageFindUser(@ModelAttribute UserCreatedDTO userCreatedDTO, Model model) {

        UserCreatedDTO foundUser = mUserService.findUserById(userCreatedDTO.getId());

        //variable for determining about displaying alert and user info
        boolean found = false;

        //the findUserById will return null object if the user was not found
        if (foundUser != null)
            found = true;

        model.addAttribute("found", found);
        model.addAttribute("userCreatedDTO", foundUser);

        return "findUserResults";
    }
}
