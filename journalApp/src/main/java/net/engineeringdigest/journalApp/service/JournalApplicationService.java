package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalApplicationService {

    @Autowired
    private JournalEntryRepository JournalEntryRepo;

    public void saveEntry(JournalEntry Entry){
        JournalEntryRepo.save(Entry);
    }

    public List<JournalEntry> getall(){
        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId myid){
        return JournalEntryRepo.findById(myid);
    }
    public boolean deletebyId(ObjectId myid){
         JournalEntryRepo.deleteById(myid);
        return true;
    }
}
