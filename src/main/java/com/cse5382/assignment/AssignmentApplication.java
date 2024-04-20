package com.cse5382.assignment;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.cse5382.assignment.Model.UserEntry;
import com.cse5382.assignment.Service.UserService;

@SpringBootApplication
@ComponentScan({"com.cse5382.assignment.Adapter", "com.cse5382.assignment.Exception", "com.cse5382.assignment.Controller", "com.cse5382.assignment.Model", "com.cse5382.assignment.Service", "com.cse5382.assignment.Repository", "com.cse5382.assignment.Security"})
public class AssignmentApplication implements CommandLineRunner {

	Logger logger = LogManager.getLogger(AssignmentApplication.class);

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
       
        UserEntry user = new UserEntry("user", "restfulAPI321GO$", "READ");
        UserEntry admin = new UserEntry("admin", "baneOfHackers123$", "READ WRITE UPDATE DELETE");

		try {
			userService.addUser(user); //user service handles password encoding
		} catch(RuntimeException e) {
			logger.info(e.getMessage());
		}
		try {
			userService.addUser(admin);
		} catch(RuntimeException e) {
			logger.info(e.getMessage());
		}
    }

	public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }
}
