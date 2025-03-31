package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        journalApplication.saveEntry(myEntry);

        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return null;
    }

    @DeleteMapping("id/{myId}")
    public JournalEntry deletemyJournalEntryById(@PathVariable Long myId){

    return null;
    }
    @PutMapping("id/{id}")
    public JournalEntry updateJournalById(@PathVariable Long id,@RequestBody JournalEntry myEntry){
        return null;
    }

}
