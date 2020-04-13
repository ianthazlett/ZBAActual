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
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Month;

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
	
	//---------------------------------------------------------------------------------VVVVVVVVVVVVVVVVVVVVVVVVVVVVV-------------------------------------------------------------------------
	//THINGS TO BE DONE:
	//Be able to search for ongoing alerts by zone
		//Junction table between zone and alert
			//Search for all instances of an alertID and get all associated zones
			//Search for all instances of an zoneID and get all associated alerts
	//Compile list of list of alerts in same order as returnZones
	//Send to front
	//Hook up with ZoneNum to display list of alerts in alerts tab

	@Override
	public void run(String... args) throws Exception {
	
		/*Object[] params = {"jimothy@gmail.com", "admin", "a a St.", "false"};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};
		int test = jdbcTemplate.update("INSERT INTO users(email, password, address, admin) VALUES(?,?,?,?)", params, types);	
		System.out.print(test);*/
		
		LocalDateTime aDateTime = LocalDateTime.of(2020, Month.APRIL, 13, 20, 00, 00);
		
		Alert alertOne = new Alert(1, "Thunderstorm", 41.349307, -79.711084, "Severe", "E", 16, aDateTime); //cranberry
		Alert alertTwo = new Alert(2, "Tornado", 42.112455, -80.063004, "Category 4", "SSE", 30, "Seek cover immediately!", aDateTime); //erie
		Alert alertThree = new Alert(3, "Flash Flooding", 41.641134, -80.151444, aDateTime); //meadville
		Alert alertFour = new Alert(4, "Winter Storm", 42.886088, -78.878685, "Mild", "ESE", 10, aDateTime); //buffalo
		Alert alertFive = new Alert(5, "Hurricane", 29.950771, -90.071675, "Category 3", "NNW", 20, "Seek cover or evacuate immediately!", aDateTime); //new orleans
		
		Alert[] alertArray = {alertOne, alertTwo, alertThree, alertFour, alertFive};
		
		
		while(true)
		{
			//get random new alert
			Random rand = new Random();
			
			int i = rand.nextInt(5);
			
			Alert testAlert = alertArray[i];
			
			//insert new alert into database
			String insertAlertQuery = String.format("INSERT INTO Alerts (name, severity, bearing, speed, action, alert_loc, expire) " +
					"VALUES ('%s', '%s', '%s', %d, '%s', ST_GeomFromText('POINT(%f %f)', 4326), '%s')",
					testAlert.getName(), testAlert.getSeverity(), testAlert.getBearing(), testAlert.getSpeed(), testAlert.getAction(), testAlert.getLongitude(), testAlert.getLatitute(), testAlert.getExpire());
			
			jdbcTemplate.update(insertAlertQuery);
			
			//get alert_id of new alert
			String alertQuery = "SELECT MAX(alert_id) FROM alerts";
			int alertID = jdbcTemplate.queryForObject(alertQuery, Integer.class);
			
			
			//get email of users with zones containing new alert
			String userQuery = String.format("SELECT email FROM users JOIN zones ON users.user_id = zones.user_id " 
					+ "WHERE ST_Contains(zone_loc::geometry, ST_GeomFromText('POINT(%f %f)', 4326))", testAlert.getLongitude(), testAlert.getLatitute());
			
			UserModel alertUser = (UserModel) jdbcTemplate.queryForObject(
	                userQuery,
	                new BeanPropertyRowMapper<UserModel>(UserModel.class));
			
			List<String> emailList = jdbcTemplate.queryForList(userQuery, String.class);
			
			//get zone_id of zones containing new alert
			String zoneQuery = String.format("SELECT zone_id FROM users JOIN zones ON users.user_id = zones.user_id " 
					+ "WHERE ST_Contains(zone_loc::geometry, ST_GeomFromText('POINT(%f %f)', 4326))", testAlert.getLongitude(), testAlert.getLatitute());
			
			
			Zone alertZone = (Zone) jdbcTemplate.queryForObject(
	               zoneQuery,
	               new BeanPropertyRowMapper<Zone>(Zone.class));
			
			List<Integer> zoneList = jdbcTemplate.queryForList(zoneQuery, Integer.class);
			
			
			for (int j = 0; j < emailList.size(); j++)
			{
				
				//map new alert to zone containing it
				String zoneAlertQuery = String.format("INSERT INTO zone_alerts (zone_id, alert_id) VALUES (%d, %d)", zoneList.get(j), alertID);
				jdbcTemplate.update(zoneAlertQuery);
				
				
				//https://www.baeldung.com/java-email
				Properties prop = new Properties();
				prop.put("mail.smtp.auth", true);
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.host", "smtp.mailtrap.io");
				prop.put("mail.smtp.port", "2525");
				prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
				
				String username = "493acfd9f96793";
				String password = "215f8f0b5b6d97";
				
				Session session = Session.getInstance(prop, new Authenticator() {
				    @Override
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication(username, password);
				    }
				});
				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("dbleagle96@gmail.com"));
				message.setRecipients(
				  Message.RecipientType.TO, InternetAddress.parse(emailList.get(j)));
				message.setSubject("Alert!");
				
				String messageText = String.format("Alert for zone %d: %s", zoneList.get(j), testAlert.getName());
	
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setContent(messageText, "text/html");
	
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(mimeBodyPart);
	
				message.setContent(multipart);
	
				Transport.send(message);
			}
		
			//sleep for 30 seconds
			Thread.sleep(30000);
		}//while(true);
		
	}

}
