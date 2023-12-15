package org.example.spring_demo_eduard_v2.repository;

import org.example.spring_demo_eduard_v2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
