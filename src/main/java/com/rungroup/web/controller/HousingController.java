package com.rungroup.web.controller;


import com.rungroup.web.dto.HousingDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.HousingService;
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

import java.security.Principal;
import java.util.List;

@Controller
public class  HousingController {
    private final HousingService housingService;
    private final UserService userService;

    @Autowired
    public HousingController(HousingService housingService, UserService userService) {
        this.housingService = housingService;
        this.userService = userService;
    }

    @GetMapping("/housing")
    public String showHousingPage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserEntity me = userService.findByUsername(principal.getName());
        boolean isAdmin = me.getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName()));
        boolean isStudent = me.getRoles().stream().anyMatch(r -> "STUDENT".equals(r.getName()));

        if (isAdmin) {
            List<HousingDto> allHousing = housingService.findAllHousing();
            model.addAttribute("housings", allHousing);
            return "housing/housing-list";
        }

        if (isStudent) {
            housingService.findByStudentId(me.getId()).ifPresent(
                    dto -> model.addAttribute("myHousing", dto)
            );
            return "housing/student-housing";
        }

        return "error/403";
    }

    @GetMapping("/housing/new")
    public String showCreateForm(Model model) {
        model.addAttribute("housingDto", new HousingDto());
        model.addAttribute("allStudents", userService.findByRole("STUDENT"));
        return "housing/housing-create";
    }

    @PostMapping("/housing/new")
    public String createHousing(@Valid @ModelAttribute("housingDto") HousingDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allStudents", userService.findByRole("STUDENT"));
            return "housing/housing-create";
        }

        HousingDto savedHousing = housingService.save(dto);
        return "redirect:/housing";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        HousingDto dto = housingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid housing Id: " + id));
        model.addAttribute("housingDto", dto);
        model.addAttribute("allStudents", userService.findByRole("STUDENT"));
        return "housing/housing-create";
    }

    @PostMapping("/{id}/edit")
    public String updateHousing(
            @PathVariable Long id,
            @Valid @ModelAttribute("housingDto") HousingDto dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("allStudents", userService.findByRole("STUDENT"));
            return "housing/housing-create";
        }
        dto.setId(id);
        housingService.save(dto);
        return "redirect:/housing";
    }

    @GetMapping("/{id}/delete")
    public String deleteHousing(@PathVariable Long id) {
        housingService.deleteById(id);
        return "redirect:/housing";
    }
}
