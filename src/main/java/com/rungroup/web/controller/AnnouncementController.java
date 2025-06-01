package com.rungroup.web.controller;

import com.rungroup.web.dto.AnnouncementDto;
import com.rungroup.web.models.Announcement;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.AnnouncementService;
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
public class AnnouncementController {
    private AnnouncementService announcementService;
    private UserService userService;
    @Autowired
    public AnnouncementController(AnnouncementService announcementService, UserService userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }

    @GetMapping("/announcements")
    public String listAnnouncements(Model model) {
        List<AnnouncementDto> announcements = announcementService.findAllAnnouncements();
        model.addAttribute("announcements", announcements);

        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            UserEntity user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        return "announcements-list";
    }

    @GetMapping("/announcements/{announcementId}")
    public String announcementDetail(@PathVariable("announcementId") long announcementId, Model model) {
        UserEntity user = new UserEntity();
        AnnouncementDto announcement = announcementService.findAnnouncementById(announcementId);
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("user", user);
        model.addAttribute("announcement", announcement);
        return "announcements-detail";
    }

    @GetMapping("/announcements/new")
    public String createAnnouncementForm(Model model) {
        Announcement announcement = new Announcement();
        model.addAttribute("announcement", announcement);
        return "announcement-create";
    }

    @PostMapping("/announcements/new")
    public String saveAnnouncement(@ModelAttribute("announcement") AnnouncementDto announcement) {
        announcementService.saveAnnouncement(announcement);
        return "redirect:/announcements";
    }

    @GetMapping("/announcements/{announcementId}/edit")
    public String editAnnouncementForm(@PathVariable("announcementId") long announcementId, Model model) {
        AnnouncementDto announcement = announcementService.findAnnouncementById(announcementId);
        model.addAttribute("announcement", announcement);
        return "announcement-edit";
    }


    @PostMapping("/announcements/{announcementId}/edit")
    public String updateAnnouncement(@PathVariable("announcementId") long announcementId, BindingResult result,
                                     @Valid @ModelAttribute("announcement") AnnouncementDto announcement) {

        if(result.hasErrors()){
            return "announcement-edit";
        }

        announcement.setId(announcementId);
        announcementService.updateAnnouncement(announcement);
        return "redirect:/announcements";
    }

    @GetMapping("/announcements/{announcementId}/delete")
    public String deleteAnnouncement(@PathVariable("announcementId") long announcementId) {
        announcementService.delete(announcementId);
        return "redirect:/announcements";
    }
}
