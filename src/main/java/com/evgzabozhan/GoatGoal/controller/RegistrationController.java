package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Role;
import com.evgzabozhan.GoatGoal.model.User;
import com.evgzabozhan.GoatGoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(Model model){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          Model model){
        User userFromDb = userRepository.findByUsername(username);

        if(userFromDb != null) {
            System.out.println("EXISTS");
            model.addAttribute("message","User exists!");
            return "auth/registration";
        }

        User user = new User();

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:auth/login";
    }
}
