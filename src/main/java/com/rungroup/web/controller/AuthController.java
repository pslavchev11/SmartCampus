package com.rungroup.web.controller;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@Controller
public class AuthController {

    private UserService  userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model){

        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            return "redirect:/register?fail";
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername());
        if(existingUsername != null && existingUsername.getUsername() != null && !existingUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }

        UserEntity existingUniqueNumber = userService.findByUniqueNumber(user.getUniqueNumber());
        if(existingUniqueNumber != null && existingUniqueNumber.getUniqueNumber() != null && !existingUniqueNumber.getUniqueNumber().toString().isEmpty()){
            return "redirect:/register?fail";
        }


        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/clubs?success";
    }
}
