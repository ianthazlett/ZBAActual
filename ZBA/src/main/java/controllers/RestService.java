package controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import models.CircleZone;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        String url = "http://localhost:8080/Login";
        return this.restTemplate.getForObject(url, String.class);
    }
    
	public CircleZone[] getCircleZoneAsObject() {
	    String url = "http://localhost:8080/Login";
	    return this.restTemplate.getForObject(url, CircleZone[].class);
	}
	
}