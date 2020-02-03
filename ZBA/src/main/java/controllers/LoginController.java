package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import models.*;

import DB.*;
import models.UserModel;


@CrossOrigin
@RestController
public class LoginController {
	

@Controller
public class IndexController {
	@RequestMapping("/")
	String login(){
	    return "login";
	    }
	}
	
	
	
    @PostMapping(path = "/UserLogin")
    public String login (@RequestParam("Email") String email, @RequestParam("Password") String password)
    {
    	UserDBController db = new UserDBController();
    	UserModel user = new UserModel();
        
        user = db.getLogin(email, password);
        
        
        if(user.getAddress().equals(""))
        {
        	System.out.print("Username or password incorrect");
            return null;
        }
        
        return user.getAddress();
        
    }
    
    @PostMapping(path = "/UserCreate")
    public void accountCreation (@RequestParam("Email") String email, @RequestParam("Password") String password, @RequestParam("Address") String address, @RequestParam("Admin") boolean admin)
    {
    	UserDBController db = new UserDBController();
        db.createUser(email, password, address, admin);   
    }
    
}