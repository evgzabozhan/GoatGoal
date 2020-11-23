package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Goal;
import com.evgzabozhan.GoatGoal.model.SubGoal;
import com.evgzabozhan.GoatGoal.model.User;
import com.evgzabozhan.GoatGoal.repository.GoalRepository;
import com.evgzabozhan.GoatGoal.repository.SubGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SubGoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private SubGoalRepository subGoalRepository;

    @GetMapping("/goal/subgoal/add")
    public String getGoalAdd(Model model){
        return "subgoal/subgoal-add";
    }

    @PostMapping("/goal/subgoal/add")
    public String postSubGoalAdd(@RequestParam String name,
                              @RequestParam String description,
                                 @RequestParam Long id, Model model) {

        Goal goal = goalRepository.findById(id).orElseThrow();

        SubGoal subGoal = new SubGoal(name,description,goal);
        subGoalRepository.save(subGoal);

        return "redirect:/goal/{id}";
    }

    @PostMapping("/goal/subgoal/{id}/edit")
    public String postSubGoalUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 Model model){
        SubGoal subGoal = subGoalRepository.findById(id).orElseThrow();
        subGoal.setName(name);
        subGoal.setDescription(description);
        subGoalRepository.save(subGoal);
        return "redirect:/goal/{id}";
    }

    @PostMapping("/goal/subgoal/{id}/remove")
    public String postSubGoalRemove(@PathVariable(value = "id") long id, Model model){
        SubGoal subGoal = subGoalRepository.findById(id).orElseThrow();
        subGoalRepository.delete(subGoal);
        return "redirect:/goal/{id}";
    }

    @GetMapping("/goal/subgoal/{id}/edit")
    public String subGoalEdit(@PathVariable(value = "id") long id, Model model){
        if (!subGoalRepository.existsById(id)) {
            return "redirect:/goal";
        }

        Optional<SubGoal> subGoal = subGoalRepository.findById(id);
        ArrayList<SubGoal> result = new ArrayList<>();
        subGoal.ifPresent(result::add);
        model.addAttribute("subgoal", result);
        return "subgoal/subgoal-edit";
    }
}