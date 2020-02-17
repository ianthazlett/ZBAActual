package team16.controllers;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import team16.models.CircleZone;
import team16.models.UserModel;

@RestController
class RestService {
	
	private static final HashMap<String, Integer> Session = new HashMap<>();
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserModel user;
    
    @GetMapping("/Login")
    public String login (@RequestParam("Email") String email, @RequestParam("Password") String password)
    {
    	try
    	{
    		UserModel loginUser = (UserModel) jdbcTemplate.queryForObject(
            "SELECT address FROM users " + "WHERE email = ?" + "AND password = ?",
            new Object[]{email, password},
            new BeanPropertyRowMapper<UserModel>(UserModel.class));
    	
    		//https://www.baeldung.com/java-random-string
    		String generatedString = "";
    		int leftLimit = 0; // letter 'a'
    		int rightLimit = 126; // letter 'z'
    		int targetStringLength = 20;
    		Random random = new Random();
    		StringBuilder buffer = new StringBuilder(targetStringLength);
    		for (int i = 0; i < targetStringLength; i++) 
    		{
    			int randomLimitedInt = leftLimit + (int) 
    			(random.nextFloat() * (rightLimit - leftLimit + 1));
    			buffer.append((char) randomLimitedInt);
    		}
    		generatedString = buffer.toString();
    		Session.put(generatedString, loginUser.getID());
    		return generatedString;
    	} catch (EmptyResultDataAccessException e) {return "";}
    }
    
    @PostMapping("/UserCreate")
    public void accountCreation (@RequestParam("Email") String email, @RequestParam("Password") String password, @RequestParam("Address") String address, @RequestParam("Admin") boolean admin)
    {
    	jdbcTemplate.update(
                "insert into users (email, password, address, admin) values(?,?,?,?)",
                email, password, address, admin);
    }

    @PostMapping("/CreateCircleZone")
    int createCircleZone(@RequestParam double center, @RequestParam int radius)
    {
    	int temp = 0;
    	//Call Createcircle DB function, assign result to temp
    	//If zone created, reopen page to display new zone
    	//else warning and don't change page to avoid deleting zone
    	return temp;
    }
    
    @PostMapping("/CreatePolygonZone")
    int createPolygonZone(@RequestParam double[][] points)
    {
    	int temp = 0;
    	//Call createPolygon DB function, assign result to temp
    	//If zone created, reopen page to display new zone
    	//else warning and don't change page to avoid deleting zone
    	return temp;
    }
}