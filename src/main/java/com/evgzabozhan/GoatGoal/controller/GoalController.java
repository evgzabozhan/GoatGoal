package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Goal;
import com.evgzabozhan.GoatGoal.model.User;
import com.evgzabozhan.GoatGoal.repository.GoalRepository;
import com.evgzabozhan.GoatGoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/goal")
    public String goal(Principal principal,Model model){
        Iterable<Goal> goals = goalRepository.findAll();

        model.addAttribute("goals",goals);
        return "goal/goal-main";
    }

    @GetMapping("/goal/add")
    public String getGoalAdd(Model model){
        return "goal/goal-add";
    }

    @PostMapping("/goal/add")
    public String postGoalAdd(@AuthenticationPrincipal User user,
                              @RequestParam String name,
                              @RequestParam String description, Model model){

        Goal goal = new Goal(name,description,user);
        goalRepository.save(goal);
        model.addAttribute("message",user.getUsername());
        return "redirect:/goal";
    }

    @GetMapping("/goal/{id}")
    public String goalInfo(@PathVariable(value = "id") long id, Model model) {
        if (!goalRepository.existsById(id)) {
            return "redirect:/goal";
        }

        Optional<Goal> goal = goalRepository.findById(id);
        ArrayList<Goal> result = new ArrayList<>();
        goal.ifPresent(result::add);
        model.addAttribute("goal", result);
        return "goal/goal-info";
    }

    @GetMapping("/goal/{id}/edit")
    public String goalEdit(@PathVariable(value = "id") long id, Model model){
        if (!goalRepository.existsById(id)) {
            return "redirect:/goal";
        }

        Optional<Goal> goal = goalRepository.findById(id);
        ArrayList<Goal> result = new ArrayList<>();
        goal.ifPresent(result::add);
        model.addAttribute("goal", result);
        return "goal/goal-edit";
    }

    @PostMapping("/goal/{id}/edit")
    public String postGoalUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 Model model){
        Goal goal = goalRepository.findById(id).orElseThrow();
        goal.setName(name);
        goal.setDescription(description);
        goalRepository.save(goal);
        return "redirect:/goal";
    }

    @PostMapping("/goal/{id}/remove")
    public String postGoalRemove(@PathVariable(value = "id") long id, Model model){
        Goal goal = goalRepository.findById(id).orElseThrow();
        goalRepository.delete(goal);
        return "redirect:/goal";
    }
}
