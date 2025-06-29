package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void TestsendMail(){
        emailService.sendMail("shankar1234bz@gmail.com","Welcome to Springboot","You are going to be massive ");
    }

}
