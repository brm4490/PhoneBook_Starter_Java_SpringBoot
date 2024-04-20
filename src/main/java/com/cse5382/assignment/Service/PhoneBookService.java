package com.cse5382.assignment.Service;

import com.cse5382.assignment.Repository.PhoneBookRepository;
import com.cse5382.assignment.Exception.InvalidPhonebookEntryException;
import com.cse5382.assignment.Model.PhoneBookEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class PhoneBookService {

    @Autowired
    PhoneBookRepository phoneBookRepository;

    private static final Logger logger = LoggerFactory.getLogger(PhoneBookService.class);

    public List<PhoneBookEntry> list() {
        logger.info("Trying to list all entries");
        return phoneBookRepository.findAll();
    }

    public void deleteByName(String name) {
        logger.info("Trying to delete entry by name: {}", name);
        List<PhoneBookEntry> entriesToFilter = phoneBookRepository.findAll();

        for (PhoneBookEntry entry : entriesToFilter) {
            if (entry.getName().equals(name)) {
                phoneBookRepository.delete(entry);
                logger.info("Deleted entry with name: {}", entry.getName());
                return;
            }
        }

        throw new NoSuchElementException("No Such Name Exists As: " + name);
    }

    public void deleteByPhoneNumber(String number) {
        logger.info("Trying to delete entry by phone number: {}", number);
        List<PhoneBookEntry> entriesToFilter = phoneBookRepository.findAll();

        for (PhoneBookEntry entry : entriesToFilter) {
            if (entry.getPhoneNumber().equals(number)) {
                phoneBookRepository.delete(entry);
                logger.info("Deleted entry with phone number: {}", entry.getPhoneNumber());
                return;
            }
        }

        throw new NoSuchElementException("No Such Number Exists As: " + number);
    }

    public void add(PhoneBookEntry phoneBookEntry) {
        logger.info("Trying to add phonebook entry with name: " + phoneBookEntry.getName());
        if(isValidName(phoneBookEntry.getName()) && isValidPhoneNumber(phoneBookEntry.getPhoneNumber())) {
            phoneBookRepository.save(phoneBookEntry);
        } else {
            throw new InvalidPhonebookEntryException("Invalid Phonebook Entry!");
        }
    }

    public boolean isValidPhoneNumber(String number) {

        if (number == null || number.isEmpty()) {
            return false; 
        }

        Pattern COUNTRY_CODE = Pattern.compile("[\\+]?\\d{1,3}");
        Pattern AREA_CODE = Pattern.compile("\\d{2,3}");
        Pattern EXTENSION = Pattern.compile("\\d{5}");
        Pattern SEP = Pattern.compile("[\\., ,-]");
        
        Pattern SUBSCRIBER_NUMBER = Pattern.compile("\\d{3}" + SEP + "\\d{4}");
        Pattern DANISH_NUMBER = Pattern.compile(
                                                "\\d{4}\\.\\d{4}" +
                                                "|" +
                                                "\\d{4}-\\d{4}" +
                                                "|" +
                                                "\\d{4} \\d{4}" +
                                                "|" +
                                                "\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{2}" +
                                                "|" +
                                                "\\d{2}-\\d{2}-\\d{2}-\\d{2}" +
                                                "|" +
                                                "\\d{2} \\d{2} \\d{2} \\d{2}" +
                                                "|" +
                                                "\\d{5}\\.\\d{5}" +
                                                "|" +
                                                "\\d{5} \\d{5}"

                                
        );

        Pattern NorthAmericanNumber = Pattern.compile( 
                                                    SUBSCRIBER_NUMBER +
                                                    "|" +
                                                    "([+]?((\\d{1,3}\\.([1].)?)|([1].)))?" + AREA_CODE + "\\." + "\\d{3}" + "\\." + "\\d{4}" +
                                                    "|" +
                                                    "([+]?((\\d{1,3}-([1]-)?)|([1]-)))?" + AREA_CODE + "-" + "\\d{3}" + "-" + "\\d{4}" +
                                                    "|" +
                                                    "([+]?((\\d{1,3} ([1] )?)|([1] )))?" + AREA_CODE + " " + "\\d{3}" + " " + "\\d{4}" +
                                                    "|" +
                                                    "([+]?((\\d{1,3}(\\.[1])?)|([1])))?" + "\\(" + AREA_CODE + "\\)" + "\\d{3}" + "\\." + "\\d{4}" +
                                                    "|" + 
                                                    "([+]?((\\d{1,3}(-[1])?)|([1])))?" + "\\(" + AREA_CODE + "\\)" + "\\d{3}" + "-" + "\\d{4}" +
                                                    "|" +
                                                    "([+]?((\\d{1,3}( [1])?)|([1])))?" + "\\(" + AREA_CODE + "\\)" + "\\d{3}" + " " + "\\d{4}"
        );
        Pattern IntraNetworkNumber = EXTENSION;
        Pattern DanishNumber =  Pattern.compile(
                                                COUNTRY_CODE    +
                                                "?"             +
                                                DANISH_NUMBER
        );
        Matcher NorthAmericanNumberMatcher = NorthAmericanNumber.matcher(number);
        Matcher IntraNetworkNumberMatcher = IntraNetworkNumber.matcher(number);
        Matcher DanishNumberMatcher = DanishNumber.matcher(number);

        return  NorthAmericanNumberMatcher.matches() ||
                IntraNetworkNumberMatcher.matches() ||
                DanishNumberMatcher.matches();
    }

    public boolean isValidName(String name) {
        Pattern NAME = Pattern.compile("((O')|(O’))?[a-zA-Z]{2,25}");
        Pattern MIDDLE_INIT = Pattern.compile("([a-zA-Z]\\.)");
        Pattern SEP = Pattern.compile("((, )|(-)| )");

        Pattern Name = Pattern.compile(NAME + "(" + SEP + NAME + ")?((" + SEP + NAME + ")|(" + SEP + MIDDLE_INIT + "))?");

        Matcher NameMatcher = Name.matcher(name);

        return NameMatcher.matches();
    }
}
