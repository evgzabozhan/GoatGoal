package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Goal;
import com.evgzabozhan.GoatGoal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @GetMapping("/goal")
    public String goal(Model model){
        Iterable<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals",goals);
        return "goal";
    }
}
