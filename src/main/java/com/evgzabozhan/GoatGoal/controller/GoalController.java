package com.evgzabozhan.GoatGoal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalController {

    @GetMapping("/goal")
    public String goal(Model model){
        return "goal";
    }
}
