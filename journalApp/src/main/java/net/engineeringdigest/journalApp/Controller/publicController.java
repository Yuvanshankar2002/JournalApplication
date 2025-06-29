package net.engineeringdigest.journalApp.Controller;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Utils.JwtUtils;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceimpl;

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

    @PostMapping("/signup")
    public void signUp(User user){
        userService.saveUser(user);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsServiceimpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwt,HttpStatus.OK);

        }
        catch (Exception e){
            log.error("Invalid Username and Password",e);
            return new ResponseEntity<>( "Incorrect Username and Password",HttpStatus.BAD_REQUEST);

        }

    }

}
