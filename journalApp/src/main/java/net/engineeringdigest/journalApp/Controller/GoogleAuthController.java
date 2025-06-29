package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

     @Value("${spring.security.oauth2.client.registration.google.client-id}")
     private String clientId;

     @Value("${spring.security.oauth2.client.registration.google.client-secret}")
     private String clientSecret;

     @Autowired
    RestTemplate restTemplate;

     @Autowired
    PasswordEncoder passwordEncoder;

     @Autowired
     UserDetailsServiceImpl userDetailsServiceimpl;

     @Autowired
    UserRepository userRepository;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallBack(@RequestParam String code){

        try{
            String tokenEndPoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String,String> Parms = new LinkedMultiValueMap<>();
            Parms.add("code",code);
            Parms.add("client_id",clientId);
            Parms.add("client_secret",clientSecret);
            Parms.add("redirect_uri","https://developers.google.com/oauthplayground");
            Parms.add("grant_type","authorization_code");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(Parms,httpHeaders);
            ResponseEntity<Map> tokenresponse = restTemplate.postForEntity(tokenEndPoint,request,Map.class);
            String idToken = (String) tokenresponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token="+idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl,Map.class);
            if(userInfoResponse.getStatusCode() == HttpStatus.OK){
                Map<String,Object> userInfo = userInfoResponse.getBody();
                String email = (String)userInfo.get("email");
                UserDetails userDetails = null;
                try {
                    userDetails = userDetailsServiceimpl.loadUserByUsername(email);
                }
                catch(Exception e) {
                    User user = new User();
                    user.setEmail(email);
                    user.setUserName(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRoles(Arrays.asList("USER"));
                    userRepository.save(user);
                    userDetails = userDetailsServiceimpl.loadUserByUsername(email);

                }


                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
          }
        catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


        }


    }
}
