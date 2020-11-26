package com.evgzabozhan.GoatGoal.controller;

import com.evgzabozhan.GoatGoal.model.Goal;
import com.evgzabozhan.GoatGoal.model.SubGoal;
import com.evgzabozhan.GoatGoal.model.User;
import com.evgzabozhan.GoatGoal.repository.GoalRepository;
import com.evgzabozhan.GoatGoal.repository.SubGoalRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Controller
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private SubGoalRepository subGoalRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/goal")
    public String goal(Principal principal,Model model){
        User user = userRepository.findByUsername(principal.getName());

        List<Goal> userGoals = StreamSupport
                .stream(goalRepository.findAll().spliterator(),false)
                .filter(goal -> goal.getAuthor().getId().equals(user.getId()))
                .collect(Collectors.toList());

        model.addAttribute("goals",userGoals);
        return "goal/goal-main";
    }

    private void goalPercent(Long id, Iterable<SubGoal> subGoal){
        System.out.println("It's start " + id);
        double count = 0;
        double doneCount = 0;
       // Iterable<SubGoal> subGoal = subGoalRepository.findAll();

        //How refactor this?
        for(SubGoal sub : subGoal) {
            if (sub.getParentGoal().getId().equals(id)) {
                count++;
                if (!sub.isActive()) {
                    doneCount++;
                }
            }
        }

        double result = (100/count) * doneCount;

        System.out.println(result);

        Goal goal = goalRepository.findById(id).orElseThrow();
        goal.setPercent(result);

        goalRepository.save(goal);

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

        Iterable<SubGoal> subGoal = subGoalRepository.findAll();
        List<SubGoal> subGoals = StreamSupport
                .stream(subGoal.spliterator(),false)
                .filter(sub -> sub.getParentGoal().getId().equals(id))
                .collect(Collectors.toList());


        if(subGoals.size() > 0) {
            goalPercent(id, subGoal);
        }

        List<Goal> result = goalRepository.findById(id)
                .map(Collections::singletonList)
                .orElse(emptyList());

        model.addAttribute("goal", result);
        model.addAttribute("subGoal",subGoals);
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

        //If remove goal, removed all sub-goals for this goal

        //remove all sub-goals
        Iterable<SubGoal> subGoals = subGoalRepository.findAll();
        for(SubGoal subGoal : subGoals){
            if (goal.getId().equals(subGoal.getParentGoal().getId())){
                subGoalRepository.delete(subGoal);
            }
        }

        //remove goal
        goalRepository.delete(goal);
        return "redirect:/goal";
    }
}
