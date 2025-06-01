package com.rungroup.web.controller;

import com.rungroup.web.dto.UserDto;
import com.rungroup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        List<UserDto> students = userService.findByRole("STUDENT");
        List<UserDto> teachers = userService.findByRole("TEACHER");

        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);
        return "admin/users-list";
    }
}
