package com.cse5382.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cse5382.assignment.Service.PhoneBookService;

@SpringBootTest
public class PhoneBookServiceRegexTest {

    @Autowired
    private PhoneBookService phoneBookService;

    @Test
    public void testValidNorthAmericanPhoneNumbers() {
        String[] validNumbers = {
                "12345",
                "(703)111-2121",
                "123-1234",
                "+1(703)111-2121",
                "1(703)123-1234",
                "12345.12345"
        };

        for (String number : validNumbers) {
            boolean expected = true;  // Expected result: Valid
            boolean actual = phoneBookService.isValidPhoneNumber(number);

            System.out.println("Expected Result: true");
            System.out.println("Actual Result: " + actual);

            assertEquals(expected, actual, "Validation failed for number: " + number);
        }
    }

    @Test
    public void testValidInternationalPhoneNumbers() {
        String[] validNumbers = {
                "+32(21)212-2324",
                "011 701 111 1234",
                "+011 1 703 111 1234"
        };

        for (String number : validNumbers) {
            boolean expected = true;  // Expected result: Valid
            boolean actual = phoneBookService.isValidPhoneNumber(number);

            System.err.println("Expected Result: true");
            System.err.println("Actual Result: " + actual);

            assertEquals(expected, actual, "Validation failed for number: " + number);
        }
    }

    @Test
    public void testInvalidPhoneNumbers() {
        String[] invalidNumbers = {
                "123",
                "1/703/123/1234",
                "Nr 102-123-1234",
                "<script>alert(“XSS”)</script>",
                "7031111234",
                "+1234 (201) 123-1234",
                "(001) 123-1234",
                "+01 (703) 123-1234",
                "(703) 123-1234 ext 204"
        };

        for (String number : invalidNumbers) {
            boolean expected = false;  // Expected result: Invalid
            boolean actual = phoneBookService.isValidPhoneNumber(number);

            System.err.println("Expected Result: " + expected);
            System.err.println("Actual Result: " + actual);

            assertEquals(expected, actual, "Validation failed for number: " + number);
        }
    }
}
