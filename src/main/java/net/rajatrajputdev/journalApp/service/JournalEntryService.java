package net.rajatrajputdev.journalApp.service;

import net.rajatrajputdev.journalApp.entity.JournalEntry;
import net.rajatrajputdev.journalApp.entity.UserEntity;
import net.rajatrajputdev.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void createEntry(JournalEntry journalEntry, String userName){
        UserEntity user = userService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.createUser(user);
    }

    public void createEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntry(ObjectId journalEntryId){
        return journalEntryRepository.findById(journalEntryId);
    }

    public void deleteEntry(ObjectId journalEntryId, String userName){
        UserEntity user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(journalEntryId));
        userService.createUser(user);
        journalEntryRepository.deleteById(journalEntryId);
    }

    public JournalEntry updateEntry(ObjectId journalEntryId, JournalEntry journalEntry) {

        JournalEntry existingEntry = journalEntryRepository.findById(journalEntryId)
                .orElseThrow(() -> new RuntimeException("Journal entry not found"));

        existingEntry.setTitle(journalEntry.getTitle());
        existingEntry.setContent(journalEntry.getContent());

        return journalEntryRepository.save(existingEntry);
    }
}
