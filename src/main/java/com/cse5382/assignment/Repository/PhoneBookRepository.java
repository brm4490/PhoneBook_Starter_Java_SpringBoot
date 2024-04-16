package com.cse5382.assignment.Repository;

import com.cse5382.assignment.Model.PhoneBookEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBookEntry, Long>{}
