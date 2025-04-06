package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalApplicationService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<User> all =   userService.getall();
        if(all!= null){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody  User myUser){
        try{
            userService.saveEntry(myUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/{username}")
    ResponseEntity<?> updateUser(@RequestBody User myUser,@PathVariable String username){
        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUserName(myUser.getUserName());
            userInDb.setPassword(myUser.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
