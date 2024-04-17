package com.cse5382.assignment.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse5382.assignment.Model.UserEntry;

@Repository
public interface UserRepository extends JpaRepository<UserEntry, UUID>{
    Optional<UserEntry> findByUsername(String username);}
