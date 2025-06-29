package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.ApiResponse.weatherResponse;
import net.engineeringdigest.journalApp.Cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class weatherService {

    @Value("${weather.api.key}")
    private  String API_KEY;

//    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public weatherResponse getWeather(String city){
        weatherResponse weatherResponse = redisService.get("weather of " + city, weatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalAPI = appCache.mp.get(AppCache.key.weather_api.toString()).replace("<API_KEY>",API_KEY).replace("<CITY>",city);
            ResponseEntity<weatherResponse>  exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, weatherResponse.class);
            weatherResponse response = exchange.getBody();
            if(response != null){
                redisService.set("weather of "+city,response, 700L);
            }
            return response;

        }
    }


}
