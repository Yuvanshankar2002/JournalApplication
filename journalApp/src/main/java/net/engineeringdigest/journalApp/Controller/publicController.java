package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class publicController {
    @Autowired
    UserService userService;
    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User myUser){
        try{
            userService.saveNewEntry(myUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}
