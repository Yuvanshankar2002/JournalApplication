package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.engineeringdigest.journalApp.Model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumer {

    @Autowired
    EmailService emailService;

//    @KafkaListener(topics ="weekly-sentiments", groupId ="weekly-sentiment-group-dev")
//    public void consumeRaw(SentimentData sentimentData) {
//        System.out.println("Raw Kafka Message: " + sentimentData);
//    }



//    @KafkaListener(topics = {"weekly-sentiments"}, groupId = "weekly-sentiment-group")
//    public void consumeRaw(String message) {
//        System.out.println("🧾 Received raw Kafka message: " + message);
//        try {
//            SentimentData data = new ObjectMapper().readValue(message, SentimentData.class);
//            System.out.println("✅ Deserialized: " + data);
//        } catch (Exception e) {
//            System.err.println("❌ Failed to deserialize: " + e.getMessage());
//        }    }

    @KafkaListener(topics = {"weekly-sentiments"},groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        sendmail(sentimentData);

    }

    public void sendmail(SentimentData sentimentData){
        emailService.sendMail(sentimentData.getEmail(),"Sentiment for Previous Week",sentimentData.getSentiment());
    }

}
