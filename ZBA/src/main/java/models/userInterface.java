package models;

public interface userInterface
{
	void UserModel();
	
	UserModel getLogin(String email, String password);
	
	void createUser(String email, String password, String address, boolean isAdmin);
	
    String getEmail();

    void setEmail(String email);
    
    String getAddress();

    void setAddress(String address);

    int getID();

    void setID(int ID);

    boolean isAdmin();

    void setAdmin(boolean admin);

	String getPassword();

	void setPassword(String password);

}

