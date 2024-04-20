package com.cse5382.assignment.Controller;

import com.cse5382.assignment.AssignmentApplication;
import com.cse5382.assignment.Exception.InvalidPhonebookEntryException;
import com.cse5382.assignment.Model.PhoneBookEntry;
import com.cse5382.assignment.Service.PhoneBookService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class Controller {

    Logger logger = LogManager.getLogger(AssignmentApplication.class);
    
    @Autowired
    PhoneBookService phoneBookService;

    @GetMapping(path = "phoneBook/list")
    public List<PhoneBookEntry> list(){
        return phoneBookService.list();
    }

    @PostMapping(path = "phoneBook/add")
    public ResponseEntity<?> add(@RequestBody PhoneBookEntry phoneBookEntry){
        try {
            phoneBookService.add(phoneBookEntry);
        } catch(InvalidPhonebookEntryException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "phoneBook/deleteByName")
    public ResponseEntity<?> deleteByName(@RequestParam String name){
        try {
            phoneBookService.deleteByName(name);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "phoneBook/deleteByNumber")
    public ResponseEntity<?> deleteByNumber(@RequestParam String number){
        try {
            phoneBookService.deleteByPhoneNumber(number);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity<Error>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
