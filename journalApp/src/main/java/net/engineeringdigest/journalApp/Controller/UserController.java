package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.ApiResponse.weatherResponse;
import net.engineeringdigest.journalApp.Scheduler.UserScheduler;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalApplicationService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.weatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    weatherService weatherService;

    @Autowired
    UserScheduler userScheduler;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<User> all =   userService.getall();
        if(all!= null){
            userScheduler.fetchUsersAndSendSaMail();
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping
    ResponseEntity<?> updateUser(@RequestBody User myUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUserName(myUser.getUserName());
            userInDb.setPassword(myUser.getPassword());
            userService.saveNewEntry(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    ResponseEntity<?> deletebyuserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUsername(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/int")
    ResponseEntity<?> Greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        weatherResponse weatherResponse = weatherService.getWeather("Chennai");
        String greeting ="";
        if(weatherResponse!= null){
            greeting = " weather feels like"+weatherResponse.getCurrent().feelsLike;

        }


        return new ResponseEntity<>("hi "+authentication.getName() + greeting,HttpStatus.OK);
    }





}
