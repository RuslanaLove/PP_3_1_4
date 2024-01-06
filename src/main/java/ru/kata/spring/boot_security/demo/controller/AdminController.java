package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String showAllUsers(ModelMap modelMap) {
        List<User> users = userService.showAllUsers();
       modelMap.addAttribute("users", users);
        return "admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam("role") String role, @ModelAttribute("user") User user) {
        userService.addNewUser(role,user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/updateUser")
    public String editUserForm(@RequestParam("id") Long id, ModelMap modelMap) {
        Optional<User> userById = userService.findById(id);

        if (userById.isPresent()) {
            modelMap.addAttribute("user", userById.get());
            modelMap.addAttribute("listRoles", roleService.findAll());
            return "/admin";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam("id") Long id,@RequestParam("role") String role, @ModelAttribute("user") User user) {
        User userUpdate = userService.findById(id).orElse(null);
        userService.updateUser(role,userUpdate);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
