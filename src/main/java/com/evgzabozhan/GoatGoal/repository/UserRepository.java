package com.evgzabozhan.GoatGoal.repository;

import com.evgzabozhan.GoatGoal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
