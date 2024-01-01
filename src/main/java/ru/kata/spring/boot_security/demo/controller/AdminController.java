package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(ModelMap modelMap) {
        List<User> allUsers = userService.showAllUsers();
       modelMap.addAttribute("users", allUsers);
        return "admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam("role") String role, @ModelAttribute("user") User user) {
        userService.addNewUser(role,user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam("id") Long id,@RequestParam("role") String role, @ModelAttribute("user") User user) {
        User userUpdate = userService.findById(id).orElse(null);
        userUpdate.setId(id);
            userService.updateUser(role,userUpdate);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
