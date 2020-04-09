package team16.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
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
    String createCircleZone(@RequestParam String Center, @RequestParam String Radius, @RequestParam String Key,
    		@RequestParam int Thunderstorm, @RequestParam int Tornado, @RequestParam int FlashFlooding, @RequestParam int WinterStorm, @RequestParam int Hurricane)
    {
    	Integer id = Session.get(Key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    	/*jdbcTemplate.update("INSERT INTO zones (user_id, zone_loc)\r\n" + 
    		"VALUES (?, ST_Transform(ST_Buffer(ST_Transform(ST_GeomFromText('POINT( ? ? )', 4326), 3857), ?, 'quad_segs=8'), 4326))", id, lng, lat, radius);*/
    		System.out.println(id);
    		
    		String temp = Center.substring(1, Center.length() - 1);
    		String[] stringList = temp.split(",");
    		
    		double lat = Double.parseDouble(stringList[0]);
    		double lng = Double.parseDouble(stringList[1]);
    		double radius = Double.parseDouble(Radius);
    		
    		String insertZoneQuery = String.format("INSERT INTO zones (user_id, zone_loc, flooding_mod, hurricane_mod, thunderstorm_mod, tornado_mod, winter_storm_mod) " 
    				+ "VALUES (%d, ST_Transform(ST_Buffer(ST_Transform(ST_GeomFromText('POINT(%f %f)',4326),3857),%f,'quad_segs=8'),4326), %d, %d, %d, %d, %d)"
    				, id, lng, lat, radius, FlashFlooding, Hurricane, Thunderstorm, Tornado, WinterStorm);
    		
    		jdbcTemplate.update(insertZoneQuery);
    		return "Complete";
    		
    	}
    }
    
    @PostMapping("/CreatePolygonZone")
    String createPolygonZone(@RequestParam String Path, @RequestParam String Key,
    		@RequestParam int Thunderstorm, @RequestParam int Tornado, @RequestParam int FlashFlooding, @RequestParam int WinterStorm, @RequestParam int Hurricane)
    {
    	Integer id = Session.get(Key);
    	if(id == null)
    	{
    		return "";
    	}
    	else
    	{
    		//jdbcTemplate.update("INSERT INTO zones (user_id, zone_loc) VALUES (?,ST_GeomFromText('POLYGON((?))',4326))", id, pointsString);
    		
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
    		
    		String insertZoneQuery = String.format("INSERT INTO zones (user_id, zone_loc, flooding_mod, hurricane_mod, thunderstorm_mod, tornado_mod, winter_storm_mod)"
    				+ "VALUES (%d, ST_GeomFromText('POLYGON((%s))',4326), %d, %d, %d, %d, %d)", id, polygonPath, FlashFlooding, Hurricane, Thunderstorm, Tornado, WinterStorm);
    		
    		jdbcTemplate.update(insertZoneQuery);
    		return "Complete";
    	}
    }
    
    @PutMapping("/UpdateCircleZone")
    String updateCircleZone(@RequestParam String Center, @RequestParam String Radius, @RequestParam String Key, @RequestParam int ID,
    		@RequestParam int Thunderstorm, @RequestParam int Tornado, @RequestParam int FlashFlooding, @RequestParam int WinterStorm, @RequestParam int Hurricane)
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
    		
    		String updateQuery = String.format("UPDATE zones SET zone_loc = ST_Transform(ST_Buffer(ST_Transform(ST_GeomFromText('POINT( %f %f )', 4326), 3857), %f, 'quad_segs=8'), 4326),"
    				+ " flooding_mod = %d,"
    				+ " hurricane_mod = %d,"
    				+ " thunderstorm_mod = %d,"
    				+ " tornado_mod = %d,"
    				+ " winter_storm_mod = %d"
    				+ " WHERE zone_id = %d", lng, lat, radius, FlashFlooding, Hurricane, Thunderstorm, Tornado, WinterStorm, ID);
    		System.out.println(updateQuery);
    		
    		jdbcTemplate.update(updateQuery);
    		return "Complete";
    	}
    }
    
    @PutMapping("/UpdatePolygonZone")
    String updatePolygonZone(@RequestParam String Path, @RequestParam String Key, @RequestParam int ID,
    		@RequestParam int Thunderstorm, @RequestParam int Tornado, @RequestParam int FlashFlooding, @RequestParam int WinterStorm, @RequestParam int Hurricane)
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
    		
    		String updateQuery = String.format("UPDATE zones SET zone_loc = ST_GeomFromText('POLYGON((%s))',4326),"
    				+ " flooding_mod = %d,"
    				+ " hurricane_mod = %d,"
    				+ " thunderstorm_mod = %d,"
    				+ " tornado_mod = %d,"
    				+ " winter_storm_mod = %d"
    				+ "WHERE zone_id = %d", polygonPath, FlashFlooding, Hurricane, Thunderstorm, Tornado, WinterStorm, ID);
    		
    		jdbcTemplate.update(updateQuery);
    		return "Complete";
    	}
    }
    
    @DeleteMapping("/DeleteZone")
    String deleteZone(@RequestParam int ID, @RequestParam String Key)
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
    ReturnZone SessionCheck(@RequestParam String Key, @RequestParam int zoneNum)
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
                    "SELECT zone_id AS zone_ID, ST_AsText(zone_loc) AS location FROM zones " + "WHERE user_id = ?",
                    new Object[]{id},
                    new BeanPropertyRowMapper<Zone>(Zone.class));
    		
    		List<UserModel> userList = jdbcTemplate.query(
                    "SELECT admin AS admin FROM users " + "WHERE user_id = ?",
                    new Object[]{id},
                    new BeanPropertyRowMapper<UserModel>(UserModel.class));

    		ReturnZone ret = new ReturnZone();
    		ret.setAdmin(userList.get(0).isAdmin());
    		ret.setZone(zoneList.get(zoneNum));
    		ret.setZoneAmount(zoneList.size());
    		
    		//stuff to get the associated alerts
    		
    		String zoneAlertQuery = String.format("SELECT alerts.alert_id, name, severity, bearing, speed, action, ST_X(alert_loc::geometry) AS latitude, ST_Y(alert_loc::geometry) AS longitude " + 
    				"FROM alerts join zone_alerts on alerts.alert_id = zone_alerts.alert_id " + 
    				"WHERE zone_id = %d", zoneList.get(zoneNum).getZone_ID());
    		
    		BeanPropertyRowMapper rowMapper = BeanPropertyRowMapper.newInstance(Alert.class);
    		
    		List<Alert> alerts = jdbcTemplate.query(zoneAlertQuery, rowMapper);	
    		
    		//Alert alert = new Alert(0, "test", 50, 50, "severe", "weast", 350, "Perish");
    		//List<Alert> alerts = new ArrayList<Alert>();
    		//alerts.add(alert);
    		ret.setAlerts(alerts);
    		
    		return ret;
    	}
    }

    @GetMapping("/AdminSearch")
    String AdminSearch(@RequestParam String Key, @RequestParam String User)
    {
    	Integer id = Session.get(Key);
    	System.out.println(Key);
    	if(id == null)
    	{
    		return null;
    	}
    	else
    	{
	    	try
	    	{	//ToDo: Fix this search
	    		
	    		String adminQuery = String.format("SELECT user_id as ID FROM users WHERE email = '%s'", User);
	    		
	    		UserModel adminSearchUser = (UserModel) jdbcTemplate.queryForObject(
	            adminQuery,
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
	    		Session.put(generatedString, adminSearchUser.getID());
	    		Session.remove(Key);
	    		return generatedString;
	    	} 
	    	catch (EmptyResultDataAccessException e) 
	    	{
	    		return "";
	    	}
    	}
    }

}