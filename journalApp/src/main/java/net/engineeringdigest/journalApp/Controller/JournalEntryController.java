package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.JournalApplication;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalApplicationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalApplicationService journalApplication;


    @GetMapping("abc")
    public List<JournalEntry> getall(){
        return journalApplication.getall();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalApplication.saveEntry(myEntry);

        return true;
    }

    @GetMapping("id/{myId}")
    public Optional<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){

        return journalApplication.findbyId(myId);
    }

    @DeleteMapping("id/{myId}")
    public boolean deletemyJournalEntryById(@PathVariable ObjectId myId){


    return journalApplication.deletebyId(myId);
    }
    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
        JournalEntry old = journalApplication.findbyId(id).orElse(null);
        if(old!=null){
            old.setTitle(myEntry.getTitle()!= null && !myEntry.getTitle().equals("")? myEntry.getTitle(): old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")? myEntry.getContent() : old.getContent());


        }
        journalApplication.saveEntry(old);

        return old;
    }

}
