package team16;

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

import java.sql.Types;
import java.util.HashMap;

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
	
		Object[] params = {"jimothy@gmail.com", "admin", "a a St.", "false"};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};
		jdbcTemplate.update("INSERT INTO users(email, password, address, admin) VALUES(?,?,?,?)", params, types);	
		
		/*UserModel loginUser = (UserModel) jdbcTemplate.queryForObject(
                "SELECT address FROM users " + "WHERE email = ?" + "AND password = ?",
                new Object[]{"jimothy@gmail.com", "admin"},
                new BeanPropertyRowMapper<UserModel>(UserModel.class));
		
		System.out.println(loginUser.getAddress());*/
		
	}

}
