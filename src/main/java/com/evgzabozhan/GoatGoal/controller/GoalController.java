package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Goal;
import com.evgzabozhan.GoatGoal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @GetMapping("/goal")
    public String goal(Model model){
        Iterable<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals",goals);
        return "goal/goal-main";
    }

    @GetMapping("/goal/add")
    public String getGoalAdd(Model model){
        return "/goal/goal-add";
    }

    @PostMapping("/goal/add")
    public String postGoalAdd(@RequestParam String name, @RequestParam String description, Model model){
        Goal goal = new Goal(name,description);
        goalRepository.save(goal);
        return "redirect:/goal";
    }

    @GetMapping("/goal/{id}")
    public String goalInfo(@PathVariable(value = "id") long id, Model model){
        System.out.println(id);
       Optional<Goal> goal =  goalRepository.findById(id);
        ArrayList<Goal> result = new ArrayList<>();
        goal.ifPresent(result::add);
       model.addAttribute("goal", result);
       return "goal/goal-info";
    }
}
