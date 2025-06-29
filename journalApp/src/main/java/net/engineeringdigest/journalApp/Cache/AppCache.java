package net.engineeringdigest.journalApp.Cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalEntry;
import net.engineeringdigest.journalApp.repository.ConfigJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum key{
        weather_api


    }


    public Map<String,String> mp;

    @Autowired
    ConfigJournalRepository configJournalRepository;

    @PostConstruct
    public void init(){
        mp = new HashMap<>();
        List<ConfigJournalEntry> all = configJournalRepository.findAll();

        for(ConfigJournalEntry it:all){
            mp.put(it.getKey(),it.getValue());

        }

    }

}
