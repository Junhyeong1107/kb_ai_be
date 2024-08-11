package com.example.sanback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sanback.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(String userid);
}
