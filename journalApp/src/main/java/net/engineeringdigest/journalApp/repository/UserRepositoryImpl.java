package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;



    public List<User> getUserForSa(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
        query.addCriteria(Criteria.where("SentimentAnalaysis").is(true));

        return mongoTemplate.find(query,User.class);


    }

}
