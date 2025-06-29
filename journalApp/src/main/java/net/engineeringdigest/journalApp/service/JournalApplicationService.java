package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Component
@Slf4j
public class JournalApplicationService {

    @Autowired
    private JournalEntryRepository JournalEntryRepo;

//    private static final Logger logger = LoggerFactory.getLogger(JournalApplicationService.class);


    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry Entry, String userName){
        try {
        User user = userService.findByUserName(userName);
        JournalEntry saved = JournalEntryRepo.save(Entry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        log.info("hahahaha");

        }
        catch (Exception e){
            throw new RuntimeException("An Error occured while saving..",e);
        }


    }

    public void saveEntry(JournalEntry Entry){
        JournalEntryRepo.save(Entry);
    }

    public List<JournalEntry> getall(){
        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId myid){
        return JournalEntryRepo.findById(myid);
    }
    @Transactional
    public void  deletebyId(ObjectId myid,String userName){
        try {

            User user = userService.findByUserName(userName);
            boolean removed  = user.getJournalEntries().removeIf(x->x.getId().equals(myid));
            if(removed){

                userService.saveUser(user);
                JournalEntryRepo.deleteById(myid);
                log.info("Removed Journal Entries...");
            }
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An Error Occured While Deleting Entry",e);
        }

    }


}
