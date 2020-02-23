package team16.models;

public class UserPOJO 
{
	private int ID;
    private boolean admin;
    private String email;
    private points[] pointsList;
    
	public int getID() 
	{
		return ID;
	}
	
	public void setID(int iD) 
	{
		ID = iD;
	}
	
	public boolean isAdmin() 
	{
		return admin;
	}
	
	public void setAdmin(boolean admin) 
	{
		this.admin = admin;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPointsList()
	{
		String ret = "";
		for(int i = 0; i < pointsList.length; i++)
		{
			ret += pointsList[i].toString();
		}
		return ret;
	}
}
