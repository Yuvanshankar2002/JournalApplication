package net.engineeringdigest.journalApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void TestsendMail(){
        redisTemplate.opsForValue().set("email","shank1234@gmail.com");
        redisTemplate.opsForValue().get("email");
        int a = 1;
    }
}
