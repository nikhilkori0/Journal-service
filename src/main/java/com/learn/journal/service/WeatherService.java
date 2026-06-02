package com.learn.journal.service;


import com.learn.journal.api.response.WeatherResponse;
import com.learn.journal.cache.AppCache;
import com.learn.journal.constants.Placeholders;
import com.learn.journal.enums.ConfigKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather-api-key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null) {
            return weatherResponse;
        } else {
            String url = appCache.getCache().get(ConfigKeys.WEATHER_API.toString())
                    .replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null) {
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }
    }
}
