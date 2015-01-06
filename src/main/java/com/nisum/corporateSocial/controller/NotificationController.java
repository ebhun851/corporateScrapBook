package com.nisum.corporateSocial.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nisum.corporateSocial.model.LoggedInUsers;
import com.nisum.corporateSocial.model.Notification;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.service.NotificationService;

@Getter
@Setter
@Controller
public class NotificationController {

	@Autowired(required=true)
	private LoggedInUsers loggedInUsers ;  
	@Autowired(required=true)
	private NotificationService notificationService ;
	
	@RequestMapping(value = "/notifications/retrieve/unread", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<Notification>> getUnreadNotifications(@RequestParam(required = true)String userName) {
			if(userName!=null & loggedInUsers.userAlreadyLoggedIn(userName)){
				List<Notification> notificaions = notificationService.getUnreadNotifications(userName);
				return new ResponseEntity<List<Notification>>(notificaions, HttpStatus.OK);
			}else {
				return new ResponseEntity<List<Notification>>(new ArrayList<Notification> (), HttpStatus.UNAUTHORIZED);
			}
	}
	
	@RequestMapping(value = "/notifications/retrieve/all", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<Notification>> getAllNotifications(@RequestParam(required = true)String userName) {
			if(userName!=null & loggedInUsers.userAlreadyLoggedIn(userName)){
				List<Notification> notificaions = notificationService.getAllNotifications(userName);
				return new ResponseEntity<List<Notification>>(notificaions, HttpStatus.OK);
			}else {
				return new ResponseEntity<List<Notification>>(new ArrayList<Notification> (), HttpStatus.UNAUTHORIZED);
			}
	}
	
}
