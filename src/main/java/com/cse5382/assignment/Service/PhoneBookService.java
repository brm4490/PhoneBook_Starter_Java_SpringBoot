package com.cse5382.assignment.Service;

import com.cse5382.assignment.Model.PhoneBookEntry;

import java.util.List;

public interface PhoneBookService {
    List<PhoneBookEntry> list();
    void add(PhoneBookEntry phoneBookEntry);
    void deleteByName(String name);
    void deleteByNumber(String phoneNumber);
}

