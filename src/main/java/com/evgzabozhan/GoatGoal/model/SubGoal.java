package com.evgzabozhan.GoatGoal.model;

import javax.persistence.*;

@Entity
public class SubGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goal_id")
    private Goal parentGoal;

    public SubGoal(){

    }

    public SubGoal(String name, String description, Goal parentGoal,boolean isActive) {
        this.name = name;
        this.description = description;
        this.parentGoal = parentGoal;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Goal getParentGoal() {
        return parentGoal;
    }

    public void setParentGoal(Goal parentGoal) {
        this.parentGoal = parentGoal;
    }
}
