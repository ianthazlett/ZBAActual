package DB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import models.UserModel;
import models.userInterface;

@Repository
public class UserDBController implements userInterface {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public UserModel getLogin(String email, String password) {
		return (UserModel) jdbcTemplate.queryForObject(
                "SELECT address FROM users " + "WHERE email = ?" + "AND password = ?",
                new Object[]{email, password},
                new BeanPropertyRowMapper<UserModel>(UserModel.class));
	}

	@Override
	public void createUser(String email, String password, String address, boolean isAdmin) {
		jdbcTemplate.update(
                "insert into users (email, password, address, admin) values(?,?,?,?)",
                email, password, address, isAdmin);
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAdmin(boolean admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UserModel() {
		// TODO Auto-generated method stub
		
	}

	

}
