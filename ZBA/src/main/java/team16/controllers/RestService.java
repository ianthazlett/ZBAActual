package team16.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import team16.models.*;

@RestController
class RestService {
	
	private static final HashMap<String, Integer> Session = new HashMap<>();
	
	ObjectMapper objectMapper = new ObjectMapper();
    
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
            "SELECT user_id as ID FROM users " + "WHERE email = ?" + "AND password = ?",
            new Object[]{email, password},
            new BeanPropertyRowMapper<UserModel>(UserModel.class));
    	
    		//https://www.baeldung.com/java-random-string
    		String generatedString = "";
    		int leftLimit = 97; // letter 'a'
    		int rightLimit = 122; // letter 'z'
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
    		System.out.println(loginUser.getID());
    		Session.put(generatedString, loginUser.getID());
    		return generatedString;
    	} catch (EmptyResultDataAccessException e) {return "";}
    }
    
    @PostMapping("/UserCreate")
    public String accountCreation (@RequestParam("Email") String email, @RequestParam("Password") String password, @RequestParam("Address") String address)
    {
    	
    	try
    	{	
    	UserModel loginUser = (UserModel) jdbcTemplate.queryForObject(
                "SELECT address FROM users " + "WHERE email = ?" + "AND password = ?",
                new Object[]{email, password},
                new BeanPropertyRowMapper<UserModel>(UserModel.class));
    	
    	return "";
    	}
    	catch (EmptyResultDataAccessException e) 
    	{
    		jdbcTemplate.update(
                "insert into users (email, password, address, admin) values(?,?,?,?)",
                email, password, address, false);
    		return "Complete";
    	}
    
    }

    @PostMapping("/CreateCircleZone")
    String createCircleZone(@RequestParam double lng, @RequestParam double lat, @RequestParam int radius, @RequestParam String key)
    {
    	Integer id = Session.get(key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    		jdbcTemplate.update("INSERT INTO zones (user_id, zone_loc)\r\n" + 
    				"VALUES (?, ST_Transform(ST_Buffer(ST_Transform(ST_GeomFromText('POINT( ? ? )', 4326), 3857), ?, 'quad_segs=8'), 4326))", id, lng, lat, radius);
    		return "Complete";
    	}
    }
    
    @PostMapping("/CreatePolygonZone")
    String createPolygonZone(@RequestParam String pointsString, @RequestParam String key)
    {
    	//Call createPolygon DB function, assign result to temp
    	//If zone created, reopen page to display new zone
    	//else warning and don't change page to avoid deleting zone
    	
    	Integer id = Session.get(key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    		//jdbcTemplate.update("INSERT INTO zones (user_id, zone_loc) VALUES (?,ST_GeomFromText('POLYGON((?))',4326))", id, pointsString);
    		return "Complete";
    	}
    }
    
    @PutMapping("/UpdateCircleZone")
    String updateCircleZone(@RequestParam String Center, @RequestParam String Radius, @RequestParam String Key,  @RequestParam int ID)
    {
    	Integer id = Session.get(Key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    	
    		String temp = Center.substring(1, Center.length() - 1);
    		String[] stringList = temp.split(",");
    		
    		double lat = Double.parseDouble(stringList[0]);
    		double lng = Double.parseDouble(stringList[1]);
    		double radius = Double.parseDouble(Radius);
    		
    		String updateQuery = String.format("UPDATE zones SET zone_loc = ST_Transform(ST_Buffer(ST_Transform(ST_GeomFromText('POINT( %f %f )', 4326), 3857), %f, 'quad_segs=8'), 4326) WHERE zone_id = %d", lng, lat, radius, ID);
    		System.out.println(updateQuery);
    		
    		jdbcTemplate.update(updateQuery);
    		return "Complete";
    	}
    }
    
    @PutMapping("/UpdatePolygonZone")
    String updatePolygonZone(@RequestParam String Path, @RequestParam String Key,  @RequestParam int ID)
    {
    	//System.out.println(Path);
    	Integer id = Session.get(Key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    		String[] splitPath = Path.split(",");
    		
    		String first = "";
    		String polygonPath = "";
    		for(int i = 0; i < splitPath.length; i += 2)
    		{
    			polygonPath += splitPath[i+1];
    			polygonPath += " ";
    			polygonPath += splitPath[i];
    			polygonPath += ",";
    			
    			if(first.equals(""))
    			{
    				first = polygonPath;
    			}
    		}
    		
    		polygonPath += first;
    		
    		polygonPath = polygonPath.substring(0, polygonPath.length() - 1);
    		
    		String updateQuery = String.format("UPDATE zones SET zone_loc = ST_GeomFromText('POLYGON((%s))',4326) WHERE zone_id = %d", polygonPath, ID);
    		
    		jdbcTemplate.update(updateQuery);
    		return "Complete";
    	}
    }
    
    @DeleteMapping("/DeleteZone")
    String deleteZone(@RequestParam String Key,  @RequestParam int ID)
    {
    	Integer userId = Session.get(Key);
    	
    	if(userId == null)
    	{
    		return "";
    	}
    	else
    	{
    		String updateQuery = String.format("DELETE FROM zones WHERE zone_id = %d", ID);
    		jdbcTemplate.update(updateQuery);
    		
    		return "Complete";
    	}
    	
    	
    }
    
    @GetMapping("/SessionCheck")
    ReturnZones SessionCheck(@RequestParam String Key)
    {
    	
    	Integer id = Session.get(Key);
    	System.out.println(Key);
    	if(id == null)
    	{
    		return null;
    	}
    	else
    	{
    		List<Zone> zoneList = jdbcTemplate.query(
                    "SELECT zone_id AS zoneID, ST_AsText(zone_loc) AS location FROM zones " + "WHERE user_id = ?",
                    new Object[]{id},
                    new BeanPropertyRowMapper<Zone>(Zone.class));
    		ReturnZones ret = new ReturnZones();
    		ret.setZoneList(zoneList);
    		
    		return ret;
    	}
    }
}