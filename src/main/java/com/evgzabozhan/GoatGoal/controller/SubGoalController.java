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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        return "redirect:/goal";
    }
}
