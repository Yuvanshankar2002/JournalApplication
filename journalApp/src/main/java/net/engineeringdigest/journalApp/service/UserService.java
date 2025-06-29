package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveNewEntry(User Entry){
        Entry.setPassword(passwordEncoder.encode(Entry.getPassword()));
        Entry.setRoles(Arrays.asList("USER"));
        userRepo.save(Entry);
    }
    public void saveUser(User user){
        userRepo.save(user);
    }


    public List<User> getall(){
        return userRepo.findAll();
    }

    public Optional<User> findbyId(ObjectId myid){
        return userRepo.findById(myid);
    }
    public boolean deletebyId(ObjectId myid){
        userRepo.deleteById(myid);
        return true;
    }

    public User findByUserName(String username){
        return userRepo.findByUserName(username);

    }

    public void deleteByUsername(String userName){
        userRepo.deleteByUserName(userName);
    }


}

