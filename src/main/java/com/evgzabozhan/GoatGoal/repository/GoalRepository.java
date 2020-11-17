package com.evgzabozhan.GoatGoal.repository;

import com.evgzabozhan.GoatGoal.model.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Long> {
}
