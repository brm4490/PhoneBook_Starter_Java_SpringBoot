package com.cse5382.assignment.Service;

import com.cse5382.assignment.Repository.PhoneBookRepository;
import com.cse5382.assignment.Model.PhoneBookEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PhoneBookServiceImpl implements PhoneBookService {

    @Autowired
    PhoneBookRepository phoneBookRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneBookServiceImpl.class);

    @Override
    public List<PhoneBookEntry> list() {
        return phoneBookRepository.findAll();
    }

    @Override
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

    @Override
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

    @Override
    public void add(PhoneBookEntry phoneBookEntry) {
        phoneBookRepository.save(phoneBookEntry);
    }
}
