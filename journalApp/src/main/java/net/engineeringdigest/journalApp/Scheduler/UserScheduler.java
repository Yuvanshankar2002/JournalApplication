package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.Enum.Sentiment;
import net.engineeringdigest.journalApp.Model.SentimentData;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    public KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Autowired
    private EmailService emailService;

     public void fetchUsersAndSendSaMail(){
         List<User> users = userRepository.getUserForSa();
         for(User user:users){
             List<JournalEntry> journalEntries = user.getJournalEntries();
             List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
             Map<Sentiment,Integer>sentimentCounts = new HashMap<>();
             for(Sentiment sentiment:sentiments){
                 if(sentiment != null){
                     sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);

                 }

             }
             Sentiment mostFrequentSentiment = null;
             int maxCount = 0;
             for(Map.Entry<Sentiment,Integer> entry :sentimentCounts.entrySet()){
                 if(entry.getValue() > maxCount){
                     maxCount = entry.getValue();
                     mostFrequentSentiment = entry.getKey();

                 }
             }

             if(mostFrequentSentiment != null){
//                 emailService.sendMail(user.getEmail(),"Sentiment for Last 7 Days",mostFrequentSentiment.toString());
                 SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for Last 7 Days "+mostFrequentSentiment).build();
                 System.out.println("➡️ Sending to Kafka: " + sentimentData);
                 kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);

             }

         }

     }

}
