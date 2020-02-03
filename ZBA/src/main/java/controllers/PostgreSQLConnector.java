package controllers;

import java.sql.*;


//https://www.tutorialspoint.com/postgresql/postgresql_java.htm
//http://www.postgresqltutorial.com/postgresql-jdbc/
public class PostgreSQLConnector {
	
	private final String url = "jdbc:postgresql://localhost:5432/alertapp";
	private final String user = "postgres";
	private final String password = "croakusite9697";

	//Establishes connection to PostgreSQL database
	//returns connection object
	public Connection connect() {
		Connection conn = null;
		
		try {
	         Class.forName("org.postgresql.Driver");
	         conn = DriverManager.getConnection(url, user, password);
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }	
		
		int id = 0;
		String query = "INSERT INTO user(password, email, address, admin) "+ "VALUES(?,?,?,?)";
		try {
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, "notadmin");
            pstmt.setString(2, "a@a.a");
            pstmt.setString(3, "7 7 street");
            pstmt.setBoolean(4, true);
            
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try  {
                	ResultSet rs = pstmt.getGeneratedKeys();
                	
                	if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
       
		} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		System.out.print("Database opened successfully!");
		return conn;
	}
	
	//finds user with specified email and password
	public String findLogin(String email, String password) {
		String query = "SELECT address " + "FROM User " + "WHERE email = ? " + "AND password = ? ";
		ResultSet rs;
		String addressString = "";
		try 
		{
			Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query);
 
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            if(!rs.next())
    		{
    			
    		}
            else 
            {
            	addressString = rs.getString(1);
            }
            
        } 
		catch (SQLException ex) 
		{
            System.out.println(ex.getMessage());
        }
		return addressString;
		
		
	}
	
	//inserts user into database
	/*public int insertUser(UserModel user) {
		String query = "INSERT INTO user(password, email, address, admin) "+ "VALUES(?,?,?,?)";
		
		int id = 0;
		
		try {
			Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getAddress);
            pstmt.setBoolean(4, user.getAdmin());
            
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try  {
                	ResultSet rs = pstmt.getGeneratedKeys();
                	
                	if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
       
		} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
	}*/
	
	public static void main(String[] args) {
        PostgreSQLConnector pgsc = new PostgreSQLConnector();
        pgsc.connect();
    }

}
