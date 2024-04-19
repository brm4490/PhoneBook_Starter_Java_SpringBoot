package com.cse5382.assignment.Service;

import com.cse5382.assignment.Repository.PhoneBookRepository;
import com.cse5382.assignment.Model.PhoneBookEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class PhoneBookService {

    @Autowired
    PhoneBookRepository phoneBookRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneBookService.class);

    public List<PhoneBookEntry> list() {
        return phoneBookRepository.findAll();
    }

    public void deleteByName(String name) {
        LOGGER.info("Trying to delete entry by name: {}", name);
        List<PhoneBookEntry> entriesToFilter = phoneBookRepository.findAll();

        for (PhoneBookEntry entry : entriesToFilter) {
            if (entry.getName().equals(name)) {
                phoneBookRepository.delete(entry);
                LOGGER.info("Deleted entry with name: {}", entry.getName());
                break;
            }
        }
    }

    public void deleteByPhoneNumber(String number) {
        LOGGER.info("Trying to delete entry by phone number: {}", number);
        List<PhoneBookEntry> entriesToFilter = phoneBookRepository.findAll();

        for (PhoneBookEntry entry : entriesToFilter) {
            if (entry.getPhoneNumber().equals(number)) {
                phoneBookRepository.delete(entry);
                LOGGER.info("Deleted entry with phone number: {}", entry.getPhoneNumber());
                break;
            }
        }
    }

    public void add(PhoneBookEntry phoneBookEntry) {
        phoneBookRepository.save(phoneBookEntry);
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
}
