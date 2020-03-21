package team16;

import team16.models.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import team16.models.UserModel;

@SpringBootApplication
public class ZbaApplication implements CommandLineRunner{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("UserController")  // Test NamedParameterJdbcTemplate
    //private userInterface userInterface;

	public static void main(String[] args) {
		SpringApplication.run(ZbaApplication.class, args);
	}
	


	@Override
	public void run(String... args) throws Exception {
	
		/*Object[] params = {"jimothy@gmail.com", "admin", "a a St.", "false"};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};
		int test = jdbcTemplate.update("INSERT INTO users(email, password, address, admin) VALUES(?,?,?,?)", params, types);	
		System.out.print(test);*/
		
		/*JSONObject paTrigger = new JSONObject();
		JSONObject timePeriod = new JSONObject();
		JSONObject end = new JSONObject();
		JSONObject start = new JSONObject();
		JSONObject conditions = new JSONObject();
		JSONObject area = new JSONObject();
		
		String timeExpression = "after";
		int startAmount = 0;
		int endAmount = 432000000;
		
		String condName = "temp";
		String condExpression = "$gt";
		int condAmount = 299;
		
		String areaType = "Polygon";
		double[][] coords = {{42.31337,-81.13905}, {39.32494,-81.255}, {39.42163,-72.92079}, {42.46941,-73.15992}};
		JSONArray JSONCoords= new JSONArray();
		for (int i = 0; i < coords.length; i++)
		{
			JSONCoords.add(coords[i]);
		}
		
		
		end.put("expression", timeExpression);
		end.put("amount", endAmount);
		
		start.put("expression", timeExpression);
		start.put("amount", startAmount);
		
		timePeriod.put("start", start);
		timePeriod.put("end", end);
		
		conditions.put("name", condName);
		conditions.put("expression", condExpression);
		conditions.put("amount", condAmount);
		
		area.put("type", "Point");
		area.put("coordinates", JSONCoords);
		
		paTrigger.put("time_period", timePeriod);
		paTrigger.put("conditions", conditions);
		paTrigger.put("area", area);
		
		System.out.println(paTrigger);*/
		
		Trigger paTrigger = new Trigger();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String requestString = objectMapper.writeValueAsString(paTrigger);
		
		System.out.println(requestString);
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("C:\\Users\\wildblood96\\Desktop\\test.json"), paTrigger);
		
		URL url = new URL("http://api.openweathermap.org/data/3.0/triggers?APPID=ebc975dd2120b3a1ae16da758ad86fd0");
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json; utf-8");
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setDoOutput(true);
	    
	    try(OutputStream os = conn.getOutputStream()) {
	        byte[] input = requestString.getBytes("utf-8");
	        os.write(input, 0, input.length);
	    }
	    
	    try(BufferedReader br = new BufferedReader(
	    		  new InputStreamReader(conn.getInputStream(), "utf-8"))) { //error code 500, not API key, server error
	    		    StringBuilder response = new StringBuilder();
	    		    String responseLine = null;
	    		    while ((responseLine = br.readLine()) != null) {
	    		        response.append(responseLine.trim());
	    		    }
	    		    System.out.println(response.toString());
	    		}
		
	}

}
