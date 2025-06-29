package net.engineeringdigest.journalApp.ApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class weatherResponse {

    public Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("observation_time")
        public String observationTime;
        public int temperature;
        @JsonProperty("weather_code")
        public int weatherCode;

        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;
        @JsonProperty("feelslike")
        public int feelsLike;


    }




}
