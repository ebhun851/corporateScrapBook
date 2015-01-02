package com.nisum.corporateSocial.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LoggedInUsers {
	private Map<String,User> loggedInUsers = new  HashMap<String,User>(); 
	
	public void putUser(User user){
		loggedInUsers.put(user.getUserName(), user);
	}
	
	public User getUser(String userName){
		return loggedInUsers.get(userName);
	}
	
	public boolean remove(String userName){
		return loggedInUsers.remove(userName)!=null?true:false;
	}
	
	public boolean userAlreadyLoggedIn(String userName){
		return loggedInUsers.get(userName)!=null? true:false;
	}
}
