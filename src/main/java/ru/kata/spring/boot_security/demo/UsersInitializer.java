package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Component
public class UsersInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public UsersInitializer(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.saveAll(List.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
        userService.addNewUser("ROLE_USER", new User("user", "userUser", 10, "hello" ));
        userService.addNewUser("ROLE_ADMIN",new User("admin", "adminAdmin", 18, "hello123" ));
    }
}