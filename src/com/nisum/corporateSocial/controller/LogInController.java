package com.nisum.corporateSocial.controller;

import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import com.nisum.corporateSocial.model.LoggedInUsers;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;
import com.nisum.corporateSocial.service.AuthenticationService;
import com.nisum.corporateSocial.service.NotificationService;
import com.nisum.corporateSocial.service.UserProfileService;
import com.nisum.corporateSocial.service.WallPostService;


@Getter
@Setter
@Controller
@RequestMapping("/userAuthentication")
public class LogInController {
	@Autowired(required=true)
	private AuthenticationService authenticationService ;
	@Autowired(required=true)
	private UserProfileService userProfileService ;
	@Autowired(required=true)
	private LoggedInUsers loggedInUsers ;  
	@Autowired(required=true)
	private User user;
	@Autowired(required=true)
	private NotificationService notificationService ;
	@Autowired(required=true)
	private WallPostService wallPostService;
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST, consumes ="application/json")
	   public ResponseEntity<String> signInAuthentication(@RequestBody(required = true)
               User user) {
			if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
				return new ResponseEntity<String>("Already logged In", HttpStatus.IM_USED);
			}else if(authenticationService.authenticate(user)){
				loggedInUsers.putUser(user);
					return new ResponseEntity<String>("Authentication successful", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Authentication failed", HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.PUT)
	public ResponseEntity<String> signUp(
			   @RequestParam(required=true)String userName,
			   @RequestParam(required=true)String password) {
		user.setUserName(userName);
		user.setPassword(password);
		if(userAlreadyRegistered(user)){
			return new ResponseEntity<String>("User Already Registered", HttpStatus.UNAUTHORIZED);
		}else{
			if(userProfileService.saveUser(user)==true){
				return new ResponseEntity<String>("User Registered With System", HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("User Registered failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/signOut/{userId}", method = RequestMethod.POST)
	   public ResponseEntity<String> signOut(
			   @PathVariable("userName") final String userName) {
		if(loggedInUsers.userAlreadyLoggedIn(userName)){
			loggedInUsers.remove(userName);
			return new ResponseEntity<String>("Logged Out Successfully", HttpStatus.OK);
		}else
			return new ResponseEntity<String>("Not a logged in User", HttpStatus.UNAUTHORIZED);
	}
	
	private boolean userAlreadyRegistered(User user){
		return authenticationService.checkAlreadyRegiestered(user);
	}
}
