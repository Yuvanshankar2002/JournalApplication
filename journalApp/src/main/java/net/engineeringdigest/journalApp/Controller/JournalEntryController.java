package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalApplicationService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalApplicationService journalApplication;
    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<?> getall(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try{
        myEntry.setDate(LocalDateTime.now());
        journalApplication.saveEntry(myEntry,userName);
        return new ResponseEntity<>(HttpStatus.CREATED);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
            Optional<JournalEntry>  journalEntry = journalApplication.findbyId(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deletemyJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        journalApplication.deletebyId(myId,userName);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry,@PathVariable String userName){
        JournalEntry old = journalApplication.findbyId(id).orElse(null);
        if(old!=null){
            old.setTitle(myEntry.getTitle()!= null && !myEntry.getTitle().equals("")? myEntry.getTitle(): old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")? myEntry.getContent() : old.getContent());
            journalApplication.saveEntry(old);

            return new ResponseEntity<>(old,HttpStatus.OK);


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
