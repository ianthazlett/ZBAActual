package models;

public class UserModel
{
    private String password;
    private int ID;
    private boolean admin;
    private String email;
    private String address;
    
    public UserModel() {
	}

    public UserModel(String email, String address, String password, boolean admin) {
    	
    	this.password = password;
        this.admin = admin;
        this.email = email;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

