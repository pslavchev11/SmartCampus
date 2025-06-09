package com.rungroup.web.controller;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.dto.UserDto;
import com.rungroup.web.mapper.RegistrationMapper;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        // fetch two separate lists
        List<UserDto> admins = userService.findByRole("ADMIN");
        List<UserDto> students = userService.findByRole("STUDENT");
        List<UserDto> teachers = userService.findByRole("TEACHER");

        model.addAttribute("admins", admins);
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);
        return "admin/users-list";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserEntity user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        RegistrationDto dto = RegistrationMapper.fromUserEntity(user);
        model.addAttribute("user", dto);
        return "admin/user-form";
    }

    @PostMapping("/admin/users/edit")
    public String processEdit(
            @ModelAttribute("user") @Valid RegistrationDto form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "admin/user-form";
        }
        userService.updateUser(form);
        return "redirect:/admin/users?updated";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users?deleted";
    }
}
