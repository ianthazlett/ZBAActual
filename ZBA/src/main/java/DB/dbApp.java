package DB;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import models.userInterface;

@SpringBootApplication
public class dbApp implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(dbApp.class);
	
	@Autowired
	  JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("UserController")  // Test NamedParameterJdbcTemplate
    private userInterface userInterface;
	
	public static void main(String[] args) {
        SpringApplication.run(dbApp.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		log.info("StartApplication...");
		
	}
}
