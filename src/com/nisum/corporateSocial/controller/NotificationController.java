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
	
	@RequestMapping(value = "/retrieve/notifications", method = RequestMethod.POST, consumes ="application/json")
	   public ResponseEntity<List<Notification>> signInAuthentication(@RequestBody(required = true)User user) {
			if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
				List<Notification> notificaions = notificationService.getNotifications(user.getUserName());
				return new ResponseEntity<List<Notification>>(notificaions, HttpStatus.OK);
			}else {
				return new ResponseEntity<List<Notification>>(new ArrayList<Notification> (), HttpStatus.UNAUTHORIZED);
			}
	}
	
}
