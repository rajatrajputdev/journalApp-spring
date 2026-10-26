package net.rajatrajputdev.journalApp.controller;
import net.rajatrajputdev.journalApp.entity.JournalEntry;
import net.rajatrajputdev.journalApp.entity.UserEntity;
import net.rajatrajputdev.journalApp.service.JournalEntryService;
import net.rajatrajputdev.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-entries/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null&&!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-entry/{userName}")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName){
        journalEntryService.createEntry(journalEntry, userName);
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }


    @GetMapping("/get-entry/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId){
        Optional<JournalEntry> journalEntry = journalEntryService.getEntry(journalId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-entry/{userName}/{journalId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId journalId, @PathVariable String userName){
        journalEntryService.deleteEntry(journalId, userName);
        return true;
    }

    @PutMapping("/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ) {
        JournalEntry old = journalEntryService.getEntry(id).orElse(null);

        if (old != null) {
            old.setTitle(
                    newEntry.getTitle() != null && !newEntry.getTitle().equals("")
                            ? newEntry.getTitle()
                            : old.getTitle()
            );

            old.setContent(
                    newEntry.getContent() != null && !newEntry.getContent().equals("")
                            ? newEntry.getContent()
                            : old.getContent()
            );

            journalEntryService.createEntry(old, userName);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
